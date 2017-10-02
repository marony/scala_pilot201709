.. pilot_doc documentation master file, created by
   sphinx-quickstart on Fri Aug 18 08:41:10 2017.
   You can adapt this file completely to your liking, but it should at least
   contain the root `toctree` directive.

Welcome to pilot_doc's documentation!
=====================================

.. コメント

.. todo:: あとで書く

.. toctree::
   :maxdepth: 2
   :caption: Contents:

   arch
   model
   logic

   pilot

**太字**

*斜体* [#f1]_

``リテラル``

`リンク付きテキスト <http://python.org>`_

.. |置換| replace:: 痴漢

|置換|

.. note:: 重要な情報です

.. warning:: 危険な情報です

数式 :math:`a^2 + b^2 = c^2`.

.. graphviz::

   digraph foo {
   // node define
   alpha [shape = box];
   beta [shape = box];
   gamma [shape = Msquare];
   delta [shape = box];
   epsilon [shape = trapezium];
   zeta [shape = Msquare];
   eta;
   theta [shape = doublecircle];

   // edge define
   alpha -> beta [label = "a-b", arrowhead = normal];
   alpha -> gamma [label = "a-g"];
   beta -> delta [label = "b-d"];
   beta -> epsilon [label = "b-e", arrowhead = tee];
   gamma -> zeta [label = "g-z"];
   gamma -> eta [label = "g-e", style = dotted];
   delta -> theta [arrowhead = crow];
   zeta -> theta [arrowhead = crow];
   }

.. uml::

   Bob -> Alice: おはよー
   Bob <- Alice: エッチ!!

.. csv-table::
   :header: A, B, C, D
	    
   1, 4, 7, 10
   2, 5, 8, 11
   3, 6, 9, 12
   てすと, てすと, てすと, てすと
   
.. [#f1] 脚注です

.. image:: haruhi.*

Indices and tables
==================

* :ref:`genindex`
* :ref:`modindex`
* :ref:`search`
