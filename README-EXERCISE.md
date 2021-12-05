A simple exercise to explore scala collections. In this exercise we are going to:

1. Print a list of users
2. Print a sublist list of users with Canadian emails
3. Send emails to users with Canadian emails
4. Report the results using a 'for loop'
5. Report the results using a 'zip' function
6. Re-write the exercise using a single stream

At the end, we should produce the following console output:
```
-- 1. Print a list of users
id: 1, name: Ash Matarazo, email: Ash.Matarazo@gmail.ca
id: 2, name: Bella Glennm, email: Bella.G@protromail.ca
id: 3, name: Chris Sexton, email: Chris.Sexton@outlook.ca
id: 4, name: David Glennm, email: David.Glennm@hotmail.com
id: 5, name: Emily Hanson, email: Emily_Hanson@yahoo.com
id: 6, name: Frank Rojasa, email: None
id: 7, name: Gabriel Grif, email: None
id: 8, name: Iris Lorensa, email: Iris-Lorensa@gmail.it
id: 9, name: James Nelson, email: James.Nelson@gmail.ca
id: 10, name: Lola Griffin, email: LolaG@yahoo.com
id: 11, name: Maya Mcguire, email: Maya.Mcguire@yahoo.ca
id: 12, name: Ross Compton, email: Ross.Compton@gmail.com
id: 13, name: Sonia Hophen, email: Sonia.Hoffman@yahoo.it
id: 14, name: Terrence Cas, email: None
id: 15, name: Tomas Mcgee, email: Tomas.Mcgee@gmail.com

-- 2. Print a sublist list of users with Canadian emails
id: 1, name: Ash Matarazo, email: Ash.Matarazo@gmail.ca
id: 2, name: Bella Glennm, email: Bella.G@protromail.ca
id: 3, name: Chris Sexton, email: Chris.Sexton@outlook.ca
id: 9, name: James Nelson, email: James.Nelson@gmail.ca
id: 11, name: Maya Mcguire, email: Maya.Mcguire@yahoo.ca

-- 3. Send emails to users with Canadian emails
Left(Gmail canada is offline)
Right(address: Bella.G@protromail.ca, body: Welcome Bella Glennm)
Right(address: Chris.Sexton@outlook.ca, body: Welcome Chris Sexton)
Left(Gmail canada is offline)
Right(address: Maya.Mcguire@yahoo.ca, body: Welcome Maya Mcguire)

---- REPORTING -----
--------------------

-- 4. Report the results using a 'for loop'
name: Ash Matarazo 	email: Ash.Matarazo@gmail.ca 	status: Failed: Gmail canada is offline
name: Bella Glennm 	email: Bella.G@protromail.ca 	status: Sent
name: Chris Sexton 	email: Chris.Sexton@outlook.ca 	status: Sent
name: James Nelson 	email: James.Nelson@gmail.ca 	status: Failed: Gmail canada is offline
name: Maya Mcguire 	email: Maya.Mcguire@yahoo.ca 	status: Sent

-- 5. Report the results using a 'zip' function
Success: 3
name: Ash Matarazo 	email: Ash.Matarazo@gmail.ca 	status: Failed: Gmail canada is offline
name: Bella Glennm 	email: Bella.G@protromail.ca 	status: Sent
name: Chris Sexton 	email: Chris.Sexton@outlook.ca 	status: Sent
name: James Nelson 	email: James.Nelson@gmail.ca 	status: Failed: Gmail canada is offline
name: Maya Mcguire 	email: Maya.Mcguire@yahoo.ca 	status: Sent

-- 6. Re-write the exercise using a single stream
name: Ash Matarazo 	email: Ash.Matarazo@gmail.ca 	status: Failed: Gmail canada is offline
name: Bella Glennm 	email: Bella.G@protromail.ca 	status: Sent
name: Chris Sexton 	email: Chris.Sexton@outlook.ca 	status: Sent
name: James Nelson 	email: James.Nelson@gmail.ca 	status: Failed: Gmail canada is offline
name: Maya Mcguire 	email: Maya.Mcguire@yahoo.ca 	status: Sent
```