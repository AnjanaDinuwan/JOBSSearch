BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "jobs" (
	"id"	INTEGER NOT NULL,
	"company_id"	INTEGER NOT NULL,
	"time"	TEXT NOT NULL,
	"name"	TEXT NOT NULL,
	"description"	TEXT NOT NULL,
	"salary"	TEXT NOT NULL,
	PRIMARY KEY("id" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "providers" (
	"id"	INTEGER NOT NULL,
	"password"	TEXT NOT NULL,
	"email"	TEXT NOT NULL,
	"company_name"	TEXT NOT NULL,
	"user_name"	TEXT NOT NULL,
	"logo"	TEXT NOT NULL,
	"contact"	TEXT NOT NULL,
	"description"	TEXT NOT NULL,
	"location"	TEXT NOT NULL,
	PRIMARY KEY("id" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "seekers" (
	"id"	INTEGER NOT NULL,
	"name"	TEXT NOT NULL,
	"username"	TEXT NOT NULL,
	"password"	TEXT NOT NULL,
	"email"	TEXT NOT NULL,
	"cv"	TEXT NOT NULL,
	"profile_photo"	TEXT NOT NULL,
	PRIMARY KEY("id" AUTOINCREMENT)
);
INSERT INTO "jobs" VALUES (1,1,'Full','Web developer','Developer who has experience in developing websites using React/HTML and CSS','Rs. 100000');
INSERT INTO "providers" VALUES (0,'','','','','','','','');
INSERT INTO "providers" VALUES (1,'password','contact@microsoft.com','Microsoft','Microsoft','','microsoft.com','This is a software company that created the Windows operating system and the Office product suite','Seattle');
INSERT INTO "providers" VALUES (2,'password','contact@google.com','Google','Google','','google.com','This is the company responsible for the creation of the Google search page, and its myriad of webpages','San Francisco');
INSERT INTO "providers" VALUES (3,'password','contact@oracle.com','Oracle','Oracle','','oracle.com','This is the company responsible for the development of the Oracle Database and the Java programming language','New York');
INSERT INTO "providers" VALUES (4,'password','contact@meta.com','Meta','Meta','','facebook.com','This is the company responsible for the development of the Facebook platform and other chat applications such as WhatsApp, Messenger and Instagram','Washington DC');
INSERT INTO "providers" VALUES (5,'password','contact@amazon.com','Amazon','Amazon','','amazon.com','This is the company responsible for the development of the Amazon E-commerce platform','Seattle');
INSERT INTO "providers" VALUES (6,'password','contact@apple.com','Apple','Apple','','apple.com','This is the company responsible for the development of the iPhone, MacBook, and the iPad. ','San Francisco');
INSERT INTO "providers" VALUES (7,'password','contact@salesforce.org','Salesforce','Salesforce','','salesforce.com','This is the company responsible for the development of the salesforce suite of products which includes Tableau and other data visualization software','Twin Cities');
INSERT INTO "providers" VALUES (8,'password','contact@openai.com','OpenAI','OpenAI','','openai.com','This is the company responsible for the development of the ChatGPT platform. It is a pioneer in the AI industry','Palo Alto');
INSERT INTO "providers" VALUES (9,'password','contact@youtube.com','Youtube','Youtube','','youtube.com','This is the company responsible for the development of the Youtube website, which is a video viewing platform','San Francisco');
INSERT INTO "providers" VALUES (10,'password','contact@twitter.com','Twitter','Twitter','','twitter.com','This is the company responsible for the development and maintenance of the popular messaging platform, Twitter. ','Dallas');
INSERT INTO "seekers" VALUES (1,'Anjana Munasinghe','anjana','password','anjana@gmail.com','','');
INSERT INTO "seekers" VALUES (2,'Lonna Pibworth','lpibworth1','niABREC','lpibworth1@marketwatch.com','','');
INSERT INTO "seekers" VALUES (3,'Davidson Capstaff','dcapstaff2','H9W3R2','dcapstaff2@google.ru','','');
INSERT INTO "seekers" VALUES (4,'Whitney Samweyes','wsamweyes3','lgcB4Bp','wsamweyes3@ox.ac.uk','','');
INSERT INTO "seekers" VALUES (5,'Elizabeth McPaik','emcpaik4','iCD9zDrAJ','emcpaik4@marriott.com','','');
INSERT INTO "seekers" VALUES (6,'Johnny Grishelyov','jgrishelyov5','RMqv2L0x','jgrishelyov5@unesco.org','','');
INSERT INTO "seekers" VALUES (7,'Olivero Legat','olegat6','47tI0pD','olegat6@istockphoto.com','','');
INSERT INTO "seekers" VALUES (8,'Ada Fuzzard','afuzzard7','GRXxQDhc','afuzzard7@ezinearticles.com','','');
INSERT INTO "seekers" VALUES (9,'Tomi Swalwel','tswalwel8','hEDCYzQi7','tswalwel8@about.com','','');
INSERT INTO "seekers" VALUES (10,'Calypso Bellringer','cbellringer9','aAAa51','cbellringer9@histats.com','','');
INSERT INTO "seekers" VALUES (11,'Juanita Stollery','jstollery0','SMdY1gjRS6K','jstollery0@mapy.cz','','');
COMMIT;
