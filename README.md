# Introduction

Application structure: 
1) DDD Structure (Just a basic portion of the DDD approach)
2) TDD (Includes 36 unit tests, Controllers, Services & local Cache)
3) Local Caching Introduced (Just a show-case I wrote small local distributing cache class)
4) 3-Tier structure (Controller->Service->Repository)
5) Swagger UI

## Installation

Postman or Swagger can be used [localhost:8080](https://localhost:8080) to run. [Swagger UI](http://localhost:8080/swagger-ui.html#/) link.

```bash
Please refer to pom.xml for the libraries that has been used.
```

## Note
I wrote a simple script to parse the data from .xlsx to .csv files (Script is not included, I just have googled it out.)
Once parsed, I will present you the structure of the MySql DB that I have used for this solution.


## Database
### Note :
You will anyway get the data as sql dumped. So this structure was just to show how I have created the tables.


```
create table if not exists category
(
	id int auto_increment
		primary key,
	name varchar(255) not null,
	parent_id int null
);

create table if not exists product
(
	id int auto_increment
		primary key,
	name varchar(255) not null,
	online_status varchar(255) not null,
	long_description TEXT(5000) not null,
	short_description varchar(255) not null
);

create table if not exists product_category
(
	product_id int not null,
	category_id int not null,
	primary key (product_id, category_id),
	constraint product_category_category_id_fk
		foreign key (category_id) references category (id),
	constraint product_category_product_id_fk
		foreign key (product_id) references product (id)
);

```

## Workarounds
Having missed making 'The full category paths for product' in the DB initially, I came up with
a workaround, in order to achieve previously missed requirement. I came up with an additional endpoint
for paths with appropriate controller for it. (Will be happy to discuss all that into the detail.)
Additional custom query can be found in the ProductRepository class, which will
retrieve mentioned paths.

- There is one thing (nothing major where I needed clarification, but I can ask that in the review meeting.)

##Please Read!
I have enjoyed doing this task, and I have spent a lot of time only for thinking what or how I should approach to its solution.
However, I would discuss other possible solutions, because I had them on my mind too.
Also, I would like to say that this doesn't mean necessarily that I would always approach the task the same, or 
that I think I couldn't even improve this one.

###Thank you all for reading this and giving me a chance. It was fun!

## Done by
Nemanja Milosavljevic, [email](nemanjovski@gmail.com) , [GitLab]("https://gitlab.com/nemanjovski"), [LinkedIn]("https://www.linkedin.com/in/nemanjovski/")