drop database if exists gardenias;
create database gardenias;

use gardenias;

create table category (
	id int auto_increment,
	name varchar(20),
	description text,
	include_date datetime,
	constraint category_pk primary key (id)
) default charset = utf8;


create table book (
	id int auto_increment,
	title varchar(50) not null,
	author varchar(30) not null,
	category_id int not null,
	publish_house varchar(50),
	publish_date datetime,
	ISBN varchar(100),
	price float,
	borrowed_times int not null default 0,
	booked_times int not null default 0,
	click_times int not null default 0,
	total int not null,
	remain int not null,
	description text,
	lendable tinyint not null default 1,
	include_date datetime not null,
	image varchar(255),
	url varchar(255),
	constraint book_pk primary key (id),
	constraint book_category_fk foreign key (category_id) references category (id)
) default charset = utf8;

create table reader (
	id int auto_increment,
	name varchar(4) not null,
	nick varchar(8) not null,
	age int not null,
	sex tinyint not null,
	account varchar(20) not null,
	password varchar(20) not null,
	ID_card varchar(18) not null,
	email varchar(25) not null,
	address varchar(30) not null,
	login_times int not null default 0,
	register_time datetime not null,
	last_login_time datetime,
	constraint reader_pk primary key (id)
) default charset = utf8;

create table lend_info (
	id int auto_increment,
	reader_id int not null,
	book_id int not null,
	lend_date datetime not null,
	return_date datetime,
	charge tinyint default 0,
	constraint lend_info_pk primary key (id),
	constraint lend_info_reader_fk foreign key (reader_id) references reader (id),
	constraint lend_info_book_fk foreign key (book_id) references book (id)
) default charset = utf8;

create table booking_info (
	id int auto_increment,
	reader_id int not null,
	book_id int not null,
	book_date datetime not null,
	cancel tinyint not null default 0,
	deal tinyint not null default 0,
	constraint booking_info_pk primary key (id),
	constraint booking_info_reader_fk foreign key (reader_id) references reader (id),
	constraint booking_info_book_fk foreign key (book_id) references book (id)
) default charset = utf8;

create table charge_info (
	id int auto_increment,
	reader_id int not null,
	book_id int not null,
	fee float not null,
	charge_date datetime,
	constraint charge_info_pk primary key (id),
	constraint charge_info_reader_pk foreign key (reader_id) references reader (id),
	constraint charge_info_book_pk foreign key (book_id) references book (id)
) default charset = utf8;

create table admin (
	id int auto_increment,
	username varchar(20) not null,
	password varchar(20) not null,
	last_login_time datetime,
	constraint admin_pk primary key (id)
) default charset = utf8;


insert into category (id, name, description, include_date) values (null, '历史', '', '2013-01-01');
insert into category (id, name, description, include_date) values (null, '文学', '', '2013-01-01');
insert into category (id, name, description, include_date) values (null, '计算机', '', '2013-01-01');
insert into category (id, name, description, include_date) values (null, '音乐', '', '2013-01-01');
insert into category (id, name, description, include_date) values (null, '美术', '', '2013-01-01');
insert into category (id, name, description, include_date) values (null, '生活', '', '2013-01-01');

insert into book (id, title, author, category_id, publish_house, publish_date, ISBN, 
	price, booked_times, click_times, total, remain, description, lendable, include_date, image, url)
		values (null, '红高粱', '莫言', 1, '花城出版社', '2012-12-12', '9787536062221',15.00, 0, 0, 5, 0,
			'《红高粱》内容简介：1986年发表的中篇小说《红高梁》，为莫言的成名之作。《红高粱》中创造了莫言的文学王国“高密东北乡”，通过“我”的叙述，描写了抗日战争期间，“我”的祖先在高密东北乡轰轰烈烈、英勇悲壮的人生故事。这部《红高梁》同时收入题材或主题相近的其他中短篇，旨在加强读者对作家莫言的主要的中篇小说，或某一方面的创作思想的了解。莫言1985年发表《透明的红萝卜》，自此引起文坛关注。小说描写了一个无名无姓的黑孩子，他坚忍地活在苦痛的现实中，以一种自虐的方式表示自己的强大。作品成功写出了一个孩子的内心世界，以及由此折射出来的外部世界。',
			1, '2013-01-01', null, 'http://book.douban.com/subject/2301308/');

insert into book (id, title, author, category_id, publish_house, publish_date, ISBN, 
	price, booked_times, click_times, total, remain, description, lendable, include_date, image, url)
		values (null, 'Java In A Nutshell, 5th Edition', 'David Flanagan', 3, 'O''Reilly Media', '2005-03-22', '9780596007737', 44.95, 0, 0, 5, 5,
			'With more than 700,000 copies sold to date, Java in a Nutshell from O''Reilly is clearly the favorite resource amongst the legion of developers and programmers using Java technology. And now, with the release of the 5.0 version of Java, O''Reilly has given the book that defined the "in a Nutshell" category another impressive tune-up. In this latest revision, readers will find Java in a Nutshell, 5th Edition does more than just cover the extensive changes implicit in 5.0, the newest version of Java. It''s undergone a complete makeover-in scope, size, and type of coverage-in order to more closely meet the needs to the modern Java programmer. To wit, Java in a Nutshell, 5th Edition now places less emphasis on coming to Java from C and C++, and adds more discussion on tools and frameworks. It also offers new code examples to illustrate the working of APIs, and, of course, extensive coverage of Java 5.0. But faithful readers take comfort: it still hasn''t lost any of its core elements that made it such a classic to begin with. This handy reference gets right to the heart of the program with an accelerated introduction to the Java programming language and its key APIs-ideal for developers wishing to start writing code right away. And, as was the case in previous editions, Java in a Nutshell, 5th Edition is once again chock-full of poignant tips, techniques, examples, and practical advice. For as long as Java has existed, Java in a Nutshell has helped developers maximize the capabilities of the program''s newest versions. And this latest edition is no different.',
			1, '2013-01-01', null, 'http://book.douban.com/subject/1466359/');

insert into book (id, title, author, category_id, publish_house, publish_date, ISBN, 
	price, booked_times, click_times, total, remain, description, lendable, include_date, image, url)
		values (null, '音乐的故事 ', '（法国）罗曼·罗兰（Rolland.R.） 译者：冷杉 代红', 4, '江苏文艺出版社', '2011-04-01', '9787539942506', 26.00, 0, 0, 5, 5,
			'《音乐的故事》是诺贝尔文学奖获奖者散文丛书之一。',
			1, '2013-01-01', null, 'http://book.douban.com/subject_search?search_text=%E9%9F%B3%E4%B9%90%E7%9A%84%E6%95%85%E4%BA%8B&cat=1001');

insert into book (id, title, author, category_id, publish_house, publish_date, ISBN, 
	price, booked_times, click_times, total, remain, description, lendable, include_date, image, url)
		values (null, '有些事现在不做,一辈子都不会做了', '韩梅梅', 6, '北方妇女儿童出版社', '2011-12-20', '9787538559590', 26.00, 0, 0, 5, 5,
			'“只需去做，生活就会改变”，《有些事现在不做，一辈子都不会做了》是一本看了就想去做点什么的好书。提供的是生活的建议，提供一些大家平时想不到，或者想到了却一直没有去做的事情，并且告诉读者，怎么去做，或者去哪里可以做到。书里讲的事，不是什么惊天动地的大事，有的只是生活中触手可及的小事。但是，生活并不是由大事组成的。这些事，并不枯燥，认真看，每一个都非常有意思，如果这一件又一件的小事都去做了，生活的质量就变了。',
			1, '2013-01-01', null, 'http://book.douban.com/subject/4260576/');

insert into book (id, title, author, category_id, publish_house, publish_date, ISBN, 
	price, booked_times, click_times, total, remain, description, lendable, include_date, image, url)
		values (null, '素描的诀窍', '伯特·多德森', 5, '上海人民美术出版社', '2005-03-01', '9787532228706', 28.00, 0, 0, 5, 5,
			'《素描的诀窍》向我们介绍了一种完整的作画方法，你可使用这种方法来作各种类型的画——即使你曾怀疑自己的作画能力。这种方法是建立在55种“绘画诀窍”之上，并循序渐进地加以介绍。而在介绍每一种诀窍时，都附有大量的实战练习，以便更好地学习。书中将会介绍一些有用的概念：比如：何为叠笔、聚变、映射、夸大；何为自由笔势、控制笔势，又如何运用；何为光影效果、深度效果、笔触笔果，又如何表现；何为“创造性作画”，又如何用来激发想像力。☆55种提高绘画能力的具体诀窍，48节帮助掌握技巧的自助课程，8张评估自身进步的自评列表，让它们带你去重新认识视觉艺术。☆伯特·多德森告诉我们：“只要能握笔，就能学会作画。”',
			1, '2013-01-01', null, 'http://book.douban.com/subject/1154707/');

insert into book (id, title, author, category_id, publish_house, publish_date, ISBN, 
	price, booked_times, click_times, total, remain, description, lendable, include_date, image, url)
		values (null, '红楼梦', '曹雪芹', 1, '人民文学出版社', '1996-12-01', '9787020002207', 59.70, 0, 0, 5, 5,
			'《红楼梦》是一部百科全书式的长篇小说。以宝黛爱情悲剧为主线，以四大家族的荣辱兴衰为背景，描绘出18世纪中国封建社会的方方面面，以及封建专制下新兴资本主义民主思想的萌动。结构宏大、情节委婉、细节精致，人物形象栩栩如生，声口毕现，堪称中国古代小说中的经 典。由红楼梦研究所校注、人民文学出版社出版的《红楼梦》以庚辰（1760）本《脂砚斋重评石头记》为底本，以甲戌（1754）本、已卯（1759）本、蒙古王府本、戚蓼生序本、舒元炜序本、郑振铎藏本、红楼梦稿本、列宁格勒藏本（俄藏本）、程甲本、程乙本等众多版本为参校本，是一个博采众长、非常适合大众阅读的本子；同时，对底本的重要修改，皆出校记，读者可因以了解《红楼梦》的不同版本状况。红学所的校注本已印行二十五年，其间1994年曾做过一次修订，又十几年过去，2008年推出修订第三版，体现了新的校注成果和科研成果。关于《红楼梦》的作者，原本就有多种说法及推想，“前八十回曹雪芹著、后四十回高鹗续”的说法只是其中之一，这次修订中校注者改为“前八十回曹雪芹著；后四十回无名氏续，程伟元、高鹗整理”，应当是一种更科学的表述，体现了校注者对这一问题的新的认识。现在这个修订后的《红楼梦》是更加完善。',
			1, '2013-01-01', null, 'http://book.douban.com/subject/1007305/');

insert into reader (id, name, nick, age, sex, account, password, ID_card, email, address, login_times, register_time, last_login_time)
		values (null, '龙凯', '爱因斯坦的狗', 21, 1, 'longkai', '123456', '45030219911013xxxx', 'im.longkai@gmail.com', '广西桂林', 0, '2013-01-01', '2013-01-01');

insert into reader (id, name, nick, age, sex, account, password, ID_card, email, address, login_times, register_time, last_login_time)
		values (null, '龙凯', '有罚款的读者', 21, 1, 'charged_user', '123456', '45030219911013xxxx', 'im.longkai@gmail.com', '广西桂林', 0, '2013-01-01', '2013-01-01');

insert into reader (id, name, nick, age, sex, account, password, ID_card, email, address, login_times, register_time, last_login_time)
		values (null, '龙凯', '有预约的读者', 21, 1, 'booked_book3_user', '123456', '45030219911013xxxx', 'im.longkai@gmail.com', '广西桂林', 0, '2013-01-01', '2013-01-01');				

insert into charge_info (id, reader_id, book_id, fee, charge_date) values (null, 2, 5, 3.8, null);
insert into booking_info (id, reader_id, book_id, book_date, cancel, deal) values (null, 3, 3, '2012-12-21', 0, 0);

	insert into admin (id, username, password, last_login_time) values (null, 'admin', 'admin', null);