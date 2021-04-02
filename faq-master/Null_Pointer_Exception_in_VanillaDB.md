# NullPointerException in VanillaDB

以下列出常見各種發生 `NullPointerException` 的情境

## Situation 1

我啟動 VanillaDB 的 server 時出現了下面這種錯誤：

```
Exception in thread "main" java.lang.NullPointerException
	at org.vanilladb.core.storage.metadata.statistics.StatMgr.initStatistics(StatMgr.java:109)
	at org.vanilladb.core.storage.metadata.statistics.StatMgr.<init>(StatMgr.java:52)
	at org.vanilladb.core.server.VanillaDb.initStatMgr(VanillaDb.java:245)
	at org.vanilladb.core.server.VanillaDb.init(VanillaDb.java:138)
	at org.vanilladb.core.server.VanillaDb.init(VanillaDb.java:79)
	at netdb.software.benchmark.server.VanillaDbSpStartUp.startup(VanillaDbSpStartUp.java:17)
	at netdb.software.benchmark.server.StartUp.main(StartUp.java:22)
```

該怎麼辦？


### Fetures

這個狀況的特徵是在於送出這個 exception 的位置 `StatMgr.initStatistics`。


### Solution

造成這個狀況的原因通常是資料庫第一次初始化時，因為不明原因終止，導致資料不完全而造成的結果。

解決辦法就是將存放資料庫的資料夾整個刪除，再重新啟動一次資料庫即可。

關於如何找到資料庫存放位置，可以參考 [這份文件][1]。

[1]: The_Location_of_Database_Files.md
