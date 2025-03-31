System consists of four microservices that handle customer data processing asynchronously using ActiveMQ.
The first microservice receives an HTTP POST request with a customer ID,
validates the customer's SID and if the validation is successful sends a message to the durable topic process.customer.topic. 
The use of a durable topic allows parallel processing rather than sequential execution, reducing latency.
Also this ensures that if one of the services fails the process does not stop entirely and partial data updates can still be made.
And durable topic is used to ensure that if consumers are unavailable, they can still process messages when they become active again.
Fullname-service and address-service subscribe to this topic. The fullname-service retrieves the customer's full name,
while the address-service fetches the customer's address. Once they process the data, they send messages with the retrieved information to persist.data.queue.
The final microservice persist-data-service listens to messages from persist.data.queue and updates the customer’s record in the database. 
