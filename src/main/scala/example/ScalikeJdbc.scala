package example

import scalikejdbc._, config._

/*
create table post(
  id int primary key,
  body text not null,
  posted_at timestamp not null
);
insert into post(id, body, posted_at) values
  (1, 'first post', '2016-01-10T10:00:00');
insert into post(id, body, posted_at) values
  (2, 'second post', '2016-01-11T10:00:00');
insert into post(id, body, posted_at) values
  (3, 'third post', '2016-01-12T10:00:00');
 */

object ScalaikeJdbcApp extends App {
  DBs.setup() // initialize connection pool

  // borrow connection
  val ids = Seq(1, 2)
  DB.readOnly { implicit session: DBSession =>
    sql"select body from post where id in (${ids})".map { resultSet =>
      println(resultSet.string("body"))
    }.list.apply()
  }
  DB.autoCommit { implicit session: DBSession =>
    // do something with autoCommit session
  }
  DB.localTx { implicit session: DBSession =>
    // do something in transaction
  }
}
