==================
パイロットシステム
==================

野菜を売買するシステム

API
===

発注
----

POST /orders/:user-id:/order/:unique-id:

.. csv-table::
   :header: 項目,型,説明

   ユニークID,,
   ユーザ,,
   野菜,,
   値段,,
   数量,,

訂正
----

POST /orders/:user-id:/replace/:unique-id:

.. csv-table::
   :header: 項目,型,説明

   ユニークID,,
   ユーザ,,
   注文ID,,
   訂正値段,,
   削減数量,,

取消
----

POST /orders/:user-id:/cancel/:unique-id:

.. csv-table::
   :header: 項目,型,説明

   ユニークID,,
   ユーザ,,
   注文ID,,

約定
----

POST /execution/:order-id:/:unique-id:

.. csv-table::
   :header: 項目,型,説明

   ユニークID,,
   発注ID,,
   約定値段,,
   約定数量,,

照会
----

GET /orders/:user-id:?検索条件

.. csv-table::
   :header: 項目,型,説明

   ユーザID,,
   発注ID,,

.. note:: 検索条件をDatalogやGraphQLで

テーブル
========

システム日付
------------

野菜
----

ユーザ
------


トランザクション
----------------

全てのトランザクションに共通の属性を格納する
挿入のみ

.. csv-table::
   :header: 項目,型,説明

   id,,トランザクションID
   seqnum,,yyyyMMddが入った全体で昇順な通番(ソートにのみ使用する)
   unique_id,,
   time,,
   category,,発注系・物系・お金系
   type,,受注・発注・発注送信・発注結果・発注エラー・訂正・取消・約定…

注文スナップ
------------

注文
----

ユーザから受け付けた注文(=発注)・訂正・取消・約定の情報を格納する
挿入のみ

.. csv-table::
   :header: 項目,型,説明

   id,,トランザクションID
   order_id,,
   user_id,,
   price,,
   quantity,,

監査ログ
--------

羃等性
------

.. note:: APIパラメータとエンティティとドメインモデル
