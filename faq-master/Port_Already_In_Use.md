# Port Already In Use
### Question

我啟動 Benchmark Client 的時候，出現錯誤，然後 Benchmark Server 跑出這個訊息：
```
java.rmi.server.ExportException: Port already in use: 1099; nested exception is:
	java.net.BindException: Address already in use
	at sun.rmi.transport.tcp.TCPTransport.listen(TCPTransport.java:340)
    ...
```
這是怎麼一回事？

#### Answer

這很有可能是因為，你之前開啟了另一個 Server (VanillaDB-Core Server 或 Benchmark Server) 沒有關閉造成的。
因為 Server 預設會使用相同的 port 啟動，所以當有其他 Server 未關閉的話，新開的 Server 就無法使用同一個 port。

如果你使用 Eclipse 的話，請檢查一下是否有其他 Console 還在運作。
如果你是用 linux 上的 terminal，請用 `ps aux | grep java` 檢查一下有沒有其他 java 程式正在執行。
假如確實發現有其他 Server 正在運作，請關閉這些 Server 再重新嘗試一次。
