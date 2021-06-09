** What assumptions did you make? **

The Amazon Autocomplete Api returns exactly 10 suggestions for each key. We need to call the Amazon API as much as prefixes on the keyword. We have to look to match the
keyword in the suggestions regarding prefix because prefix matched words can mislead as they can fetch another item list. I assume keyword orders important for the
Amazon API, because Amazon Api sorts suggested list according to the words in the keyword.


** How does your algorithm work? **

My algorithm works based on the weights of each word in keyword and weights of prefixes in each word. The score calculation is done for each word in keyword separately by
multiplying each word's weight and then sum each score. In the first step, I am splitting keyword into words, storing the words in a list, and calculating the weights for 
each word. Weights of each word are calculated as below formula:
	
	*	wordWeight = (size of the list - index of word in the list) / (size of the list * (size of the list + 1) / 2)

The word score calculated based on a subset of strings that are contained in the word. For each word, the Amazon Autocomplete endpoint is called n times, where n is the size 
of the word. The first call, the substring value is the first letter of the word. The second call, the substring value is the first two letters of the word. The process goes 
on until one of the following statements is true:
    *	The complete word is sent
    *  	The word appears in all autocomplete options from a substring

The substring score is calculated every time the Amazon Autocomplete endpoint is called. Each substring is considered to have the same importance or weight. An occurrence 
happens when the complete word appears in one element of the substring autocomplete options. A word score is defined as the summation of the word occurrences of all substrings 
contained in the word. Weights of prefixes for each word are calculated as below formula:
	
	*	score = score + (prefix weight * count of words in the suggestion) / (size of the suggestion)
	
* Example: *
For "iphone charger" keyword, each substring of "iphone" word has 16.7% weight. The following substrings are created:
	
	substring		i	  ip	iph	  ipho	iphon	iphone
	occurrences		4	  7 	9	  10	10		10
	score			6.7   11.7	15    16.7	16.7	16.7
"iphone" has a search volume estimation of 83.5.
The same procedure repeats for "charger" word. Each substring of "charger" word has 14.3% maximum weight. The following substrings are created:

	substring		c	ch	cha	   char		charg	 charge		charger
	occurrences		0	0	1	   1		4		 7			10
	score			0	0	1.4    1.4		5.6		 9.8		14.3
"charger" has a search volume estimation of 32.5.

Each word has a weight, in our example weight of "iphone" word is 0.7 and weight of "charger" word is 0.3 , so the total score is score = 83.5 * 0.7 + 32.5 * 0.3 = 68.2, 
which rounds up to 62. 


** Do you think the (*hint) that we gave you earlier is correct and if so - why? **

I think the hint is not correct as it is mention in the above assumptions that "Sort/Return: Sort the Candidate-Set by search-volume and return the top 10 results", hence 
the order is important to Amazon Autocomplete API. And also in my implementation, the hint is not meaningful, because for the score calculation I am not considering the 
position of the keyword in the suggestion list, if I considered it then the hint would be meaningful in my implementation.


** How precise do you think your outcome is and why? **

According to the given information, I think it is impossible to calculate the accuracy of my score because Amazon API is a live prod API. In each call, I will get a different
suggestions list. As we don't have the previous data it is not possible to compare the results. Again according to the provided information it makes sense for me that my
algorithm good enough, because during the "iphone" search there is only one way, but for the search of "iphone charge" there are 2 combinations possible for search keyword which 
affecting the final score. But if we could get more information regarding the weight of words in the keyword from the Amazon API, we could calculate the score more accurately.


** Build and Run: **

It is a maven Spring-Boot project with Java 8 version to build the application, in pom.xml the path below command should be runned:
mvn clean install -U

-U required if any dependency not exist locally, it will download it.

In the same location, with the following command the application could be run:
mvn spring-boot:run

Above command will start the Tomcat container on the 8080 port. For terminating the application, need to use:
ctrl+c

Swagger UI is implemented, so the API Documentation can accessed using the following URL in the browser:
http://localhost:8080/swagger-ui.html#

For calling the implemented API below GET request could be used:
http://localhost:8080/api/estimate?keyword=word