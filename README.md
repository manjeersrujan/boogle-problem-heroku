# boogle-problem-heroku
Sample webapp to deploy on heroku

Assumptions:


1) This is a single machine application (If not, ID generation, playing action need to be atomic in a distributed environment. We should use some database and distributed locks(like zookeeper) to handle this)

2) Thread safety is not needed for the test. (If not, the above solution applies for this problem too.

3) The status of the application need not be persistent for a long time. So, I used HEROKU which doesn't manage the server side session. (Redis could be a simple solution for this which is provided by HEROKU. But, I didn't want to make it more complex for exercise)

4) On HEROKU, "test with correct word" test fails. Because the network latency is high and the board expires caused by the low duration which is just 1000ms. Heroku data centers are in Europe and the US only and it causes high latency. Google AppEngine can work as it has data centers in Asia (Or any other which has Data centers nearby). We can fix that test in the following ways.
   a. We can increase the duration for the "plays game with correct word" test.
   b. Use cloud which has a data center at a nearby location.
   c. Run in local

5) Unit test cases are not needed. (If not, need to add tests for each public method of each class)

6) The boggle board is 4X4 and the size doesn't change. If not, The finding words solution can be extended easily and the whole project needs minimal changes.

7) Added comments only when the method's name is not self-explanatory

Hosted location: https://boogle-game.herokuapp.com



