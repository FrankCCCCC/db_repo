# Assignment 2

這邊匯集了許多作業問題，大家寫作業對於 Spec 有任何問題可以先詳閱此處是否有類似狀況。 如果找不到相似問題的話，再向助教寄信詢問或在 Issues 發問。

## Hints
> 學生: 作業都不知道該從哪裡開始啊~~~ 到底該增加甚麼 class，哪裡要修改都沒概念 QQ

因為似乎許多同學看到如此大的 project，都不知道該從哪下手。 Trace code 完之後似乎也沒有概念要新增甚麼東西，所以這裡給一點提示。

依照我們的設計，若你今天想要實作 UpdateItemPriceTxn，你可能要增加以下幾個 class：
- `org.vanilladb.bench.benchmarks.as2.rte.UpdatePriceParamGen`
  - 給 UpdateItemPriceTxn 用的 parameter generator
- `org.vanilladb.bench.benchmarks.as2.rte.jdbc.UpdatePriceJdbcJob`
  - UpdateItemPriceTxn 的 JDBC 實作
- `org.vanilladb.bench.server.param.as2.UpdatePriceProcParamHelper`
  - Server 端解析 UpdateItemPriceTxn 的 parameter 的小助手
- `org.vanilladb.bench.server.procedure.as2.UpdatePriceProc`
  - UpdateItemPriceTxn 的 Stored Procedure 實作

除此之外，也有其他地方要改，例如你可能需要在 `org.vanilladb.bench.benchmarks.as2.As2BenchTxnType` 裡面新增一個新的 Type。 至於實際上 benchmarks 要怎麼切換這些 class，就由各位自行研究出來囉！

祝大家作業順利

## FAQ

### Question 1: `READ_WRITE_TX_RATE` 的定義
> L 君: 請問調 ReadItemTxn 跟 UpdateItemPriceTxn 的 Ratio 是指說 1) 多個 RTE 跑不同 Txn , 2) 同一個 RTE 跑多個 Txn?

這個要求的意思是說，在一個 RTE 選擇要跑哪一種 txn (transaction 的縮寫) 時，有多少機率會選到 UpdateItemPriceTxn。 順便給各位一個提示，大家在 trace code 時應該可以看到 `org.vanilladb.bench.benchmarks.as2.rte.As2BenchRte` 有一個 `getNextTxType()` 的 method。 這個 method 設計的目的就是用來決定接下來要跑哪種 txn。 因此可以想一下怎麼利用這點。

### Question 2: 要怎麼知道 VanillaCore 有哪些 API 可以呼叫？
> M 君: 我們看不到 VanillaCore 內部的程式碼，請問我們要怎麼知道有甚麼 API 可以呼叫？

VanillaCore 的 API 可參考下列網址中的文件：
[https://nthu-datalab.github.io/db/docs/core-0.3.1-javadoc/index.html](https://nthu-datalab.github.io/db/docs/core-0.3.1-javadoc/index.html)

### Question 3: 為什麼我下 `UPDATE ...` 會得到 `BadSyntaxException` 的訊息？
> M 君: 我看 `ReadItemTxnJdbcJob` 中下 SQL 是用 `executeQuery`，所以我下 `UPDATE ...` 指令時也是用同樣方法，但是為什麼會得到 `BadSyntaxException` (語法錯誤) 的訊息？

這是因為 JDBC 的 API 在下達 `UPDATA` 指令時，應該使用 `executeUpdate`。 因為 `executeQuery` 只是用來查詢資料的 method，任何要修改資料的動作都必須使用 `executeUpdate`。

### Question 4: ResultSet 要放甚麼？
> K 君: 如果是 UpdateItemPriceTxn 的 ResultSet 裡面要放什麼呢？

我們並沒有限定要放甚麼資料進去，不過記得最少必須要放 `status` 跟 `committed` 的資料進去，不然系統會判斷你的 Transaction 執行失敗，詳情請參考 `org.vanilladb.bench.server.param.as2.ReadItemProcParamHelper`。

### Question 5 : AS2 相關
> H 君: as2 到底是啥意思啊？程式碼中到處都看得到

as2 就是 assignment 2 的意思哦~
