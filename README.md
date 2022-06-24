# Copyto 3.0



## Popis aplikace

Copyto je portál pro copywritery a zadavatele zakázek, který slouží jako systém pro freelancery zabývající se copywritingem a vlastníky či správce webů, redaktory časopisů a magazínů a dalších osoby hledající copywritery pro úpravy článků a textů na svých webech. 


## Technologie a jazyk

Java, SpringBoot, Junit5, Mockito


## Relační databáze
Databáze obsahuje tabulky:
* User
* Order
* Categorie
* Workplace
* Version


## Сache

K vytvoření clusterové cache jsme použili Springframework Cache a Hazelcast.
Cache se používá pro `UserService` a `OrderService` 

## Messaging

Použili jsme Kafku na Messaging. Kafka je zvláštní microservisa ve zvláštním balíčku.

## Authorization

Aplikace používá basic authorization v package `User.security`

## Inteceptors

jsme použili Inteceptors k vytvoření logů `GeneralInterceptor` a `AuthorizationInterceptor`

## REST

Aplikace používá REST API v `CategoryController`, `OrderController`, `UserService`

## Nasazeni

Aplikace nasazená na Heroku.

## Elasticsearch

Začal ji implementovat, ale nepodařilo se mu ji integrovat do projektu.

## Design pattern 

* State Machine `OrderState`
* Memento `Version`, `Workplace`
* Builder `Order`, `OrderBuilder`
* Factory `UserFactory`
* Command `UserRegistrationRequest`, `CreateOrderRequest`
* DAO

## Inicializační postup

Naklonovat repozitář. Stáhnout docker. Spustit soubor `docker-compose.yml`. Otevřít `localhost:5050` v prohlížeči. PgAdmin master password `password`. Vytvořit nový server: hostname `postgres`, user `chereole` a heslo `password`. A vytvořit dvě databáze `User` a `Order`. Spustit servis `Eureka`, pak `User`, `Order` a `Kafka`.



