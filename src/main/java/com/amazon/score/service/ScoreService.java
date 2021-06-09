package com.amazon.score.service;

import com.amazon.score.client.AmazonClientAPI;
import com.amazon.score.dto.ResponseDTO;
import com.amazon.score.enums.AmazonAPIEnum;
import com.amazon.score.enums.InvalidKeywordEnum;
import com.amazon.score.exception.AmazonAPIException;
import com.amazon.score.exception.InvalidKeywordException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class ScoreService implements IScoreService {

    private static final Logger LOG = LoggerFactory.getLogger(ScoreService.class);

    private AmazonClientAPI amazonAutocompleteApi;

    /**
     * Returns the score of a keyword.
     *
     * @param keyword input keyword to calculate the score.
     * @return
     */
    @Override
    public ResponseDTO estimateScore(String keyword) {

        LocalTime start = LocalTime.now();
        LOG.info(String.format("Score calculation started: %s", start));

        if (keyword == null || keyword.isEmpty() || keyword.trim().isEmpty()) {
            throw new InvalidKeywordException(InvalidKeywordEnum.INVALID_KEYWORD);
        }

        Long score = getScoreByWordWeight(keyword);

        LocalTime end = LocalTime.now();
        LOG.info(String.format("Score calculation ended: %s", end));

        Integer calculationTime = Math.toIntExact(Math.round(Double.valueOf(Duration.between(start, end).toMillis()) / 1000));
        LOG.info(String.format("Calculation process time: %s", calculationTime));

        return new ResponseDTO().builder().keyword(keyword).score(score).build();
    }

    /**
     * Returns the sum of all word scores.
     *
     * The keyword score calculated based on weights of words in the keyword. Keyword is split to the words
     * by whitespace, and stored in the list. Each word is considered to have different importance or weight (see wordWeight).
     * Weights are calculated regarding occurrence place in the words list.
     *
     * Note: A round up approach is used to convert from decimal to integer.
     *
     * @param keyword input keyword to calculate the score.
     * @return
     */
    private Long getScoreByWordWeight(String keyword) {
        Double score = 0D;
        Double j;
        Double wordWeight;
        List<String> keywords = Arrays.asList(keyword.trim().split("\\s+"));

        for (int i = 0; i < keywords.size(); i++) {
            j = Double.valueOf(keywords.size() - i);
            wordWeight = j / (keywords.size() * (keywords.size() + 1) / 2);
            score += getScore(keywords.get(i).toLowerCase()) * wordWeight;
        }

        return Math.round(score);
    }

    /**
     * Returns the score of a word.
     *
     * The word score calculated based on a subset of strings that are contained in the word.
     * For each word, the Amazon Autocomplete endpoint is called n times, where n is the size of the word.
     * The first call, the substring value is the first letter of the word. The second call, the substring
     * value is the first two letters of the word. The process goes on until one of the following statements is true:
     *      The complete word is sent
     *      The word appears in all autocomplete options from a substring
     *
     * The substring score is calculated every time the Amazon Autocomplete endpoint is called. Each substring
     * is considered to have the same importance or weight (see subWeight). An occurrence happens when the
     * complete word appears in one element of the substring autocomplete options. A word score is defined as the
     * summation of the word occurrences of all substrings contained in the word.
     *
     * @param word input word to calculate the score.
     * @return
     */
    private Double getScore(String word) {

        Integer length = word.length();
        Double subWeight = 100D / length;
        Double score = 0D;
        Long count;

        for (int i = 0; i < length; i++) {
            String prefix = word.substring(0, i + 1);
            List<String> suggestions = getSuggestionsAmazonAutocomplete(prefix);

            if (suggestions == null) {
                throw new AmazonAPIException(AmazonAPIEnum.AMAZON__API_ISSUE);
            }

            count = suggestions.stream().filter(str -> str.contains(word)).count();

            if (count == suggestions.size()) {
                score += subWeight + (length - (i + 1)) * subWeight;
                break;
            }

            score += subWeight * count / suggestions.size();
        }

        return score;
    }

    /**
     * Returns parsed suggestions from the autocomplete amazon search.
     *
     * @param keyword input keyword to search in Amazon API.
     * @return List of suggestions
     */
    private List<String> getSuggestionsAmazonAutocomplete(String keyword) {
        String response = amazonAutocompleteApi.complete(keyword);
        String[] split = response.replaceAll("\\[\\\".*\\\",\\[","").replaceAll("\\],\\[.*?\\\"\\]","").split(",");
        return Arrays.asList(split);
    }
}
