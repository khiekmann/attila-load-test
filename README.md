# attila-load-generator  [![Build Status](https://travis-ci.org/khiekmann/attila-load-test.svg?branch=master)](https://travis-ci.org/khiekmann/attila-load-test)
A simple load tester in java, named after a good friend.

## How to use this load tester

Everything starts with exting *UseCaseable.interface* with your's 
own class and thereby defining your own use case. The example at 
hand, attila, has its use case in send specially crafted messages 
to one-to-many urls. If your use case is more complex than that, 
then this load tester is not the out-of-the-box one for you (but 
you could put more complex http interaction a class interfacing 
Sendable).

**Messages**

The messages to be sent by Attila have a special structure. You can 
have your own messages, just extend *Message.class* and have them
managed in *Messages.class*. The later wraps a list of 
*Message.class*es. 

**Sender**

Sending is done by *Sending.class* and executes through the 
following steps:
* Create the URL connection
* Write a message to that URL Connection
* Read response code of that writing action
* Disconnect URL connection
Attila uses POST connections, but you are free to programm your URL Connection creator against SendingCreate.class.

The Sending That is all you need for your use case.

**Load testing**

The above defines a use case. A test case is a use case with a data 
object that holds information about the execution of a use case
(starting time, ending time, duration). The test case is then put 
in a *TestCaseExecutor.class* and the executor then takes care of 
the load test and returns a result data object.

### Class dependency diagram
``` 
TestCaseExecutor attilaRunner = new TestCaseExecutor(testCase);
+---TestCase testCase = new TestCase(attilaUseCase, data);
    +---UseCaseable attilaUseCase = new AttilaUseCase(List<String> messages, attilaSender);
    |   +---Sendable attilaSender = new Sending(attilaConnectionCreate);
    |       +---SendingCreate attilaConnectionCreate = AttilaConnectionCreate.createInstance(getURL());
    +---DataForTestCase data = new DataForTestCase();
```

### TODO:
* Multiple urls
* Log sent messages trace id
* Log actions to stdout or log file