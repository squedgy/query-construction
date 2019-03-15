create table test
(
  testId    serial primary key,
  name      text    not null,
  bool      boolean not null,
  shortName text
);

create table anotherTest
(
  anotherTestId serial primary key,
  aDateTime     timestamp not null,
  aDate         date      not null,
  aTime         time      not null,
  testId        int       not null
    constraint fk_anotherTest_test references test (testId)
);

create table aTable
(
  aTableId       serial primary key,
  intValue       int,
  textValue      text,
  booleanValue   boolean,
  timestampValue TIMESTAMP
);

insert into test (name, bool, shortName)
VALUES ('jeffrey', true, 'jeff'),
       ('david', false, 'dave'),
       ('samantha', true, 'sam'),
       ('richard', true, 'dick'),
       ('sanguimancy', true, 'evil'),
       ('scoundrel', true, 'kid');

insert into anotherTest (aDateTime, aDate, aTime, testId)
VALUES ('2000-01-21 12:15:15', '2016-01-21', '15:35:00', 1),
       ('1960-10-09 11:15:15', '1985-06-21', '01:00:00', 3),
       ('1998-04-11 11:15:15', '2004-03-04', '09:23:52', 2),
       ('1970-01-01 00:00:00', '1971-07-02', '22:34:47', 5),
       ('2012-02-02 06:53:23', '2030-02-02', '06:53:23', 6);

insert into aTable (intValue, textValue, booleanValue, timestampValue)
values (57, 't', false, '2020-4-20 00:00:00'),
       (45000, 't text', true, '2019-4-20 00:00:00'),
       (23345, 't text', true, '3024-4-20 00:00:00'),
       (40000, 't', false, '2089-4-20 00:00:00'),
       (70000, 't', false, '2022-4-20 00:00:00'),
       (90867, 't text', false, '1989-4-20 00:00:00'),
       (12345, 't text', true, '1045-4-20 00:00:00'),
       (900, 'f', true, '1940-4-20 00:00:00');
