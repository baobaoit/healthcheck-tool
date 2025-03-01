# EWM health check tool :toolbox:
This is a small tool to check whether the service at the port is up or not. If a service is down, an email will be sent
to the administration level from [noreply@worldcurrency.cards](mailto:noreply@worldcurrency.cards)

## Development :gear:
### Run with Maven:
To start your application in the dev profile, simply run:
```bash
./mvnw spring-boot:run -Dspring-boot.run.arguments="-p=port1,port2,...,portN --to=email@gmail,em.ail@gmail,..."
```
### Run with Java :coffee::
To build your application in the dev profile, run:
```bash
./mvnw -Pdev clean package
```
To run your application:
```bash
java -jar target/*.jar -p=port1,port2,...,portN --to=email@gmail,em.ail@gmail,...
```
## Production
### Run with Maven:
To start your application in the prod profile, simply run:
```bash
./mvnw spring-boot:run -Dspring-boot.run.arguments="-p=port1,port2,...,portN --to=email@gmail,em.ail@gmail,..." -Dspring-boot.run.jvmArguments="-Dspring.profiles.active=prod"
```
### Run with Java :coffee::
To build your application in the prod profile, run:
```bash
./mvnw -Pprod clean package
```
To run your application:
```bash
java -Dspring.profiles.active=prod -jar target/*.jar -p=port1,port2,...,portN --to=email@gmail,em.ail@gmail,...
```
