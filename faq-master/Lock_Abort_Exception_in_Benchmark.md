# LockAbortException in Benchmark

以下列出常見各種發生 `LockAbortException` 的情境

## Situation 1

我執行 Benchmark 時 ， server 時出現了下面這種錯誤：

```
org.vanilladb.core.storage.tx.concurrency.LockAbortException
	at org.vanilladb.core.storage.tx.concurrency.LockTable.avoidDeadlock(LockTable.java:112)
	at org.vanilladb.core.storage.tx.concurrency.LockTable.xLock(LockTable.java:221)
	at org.vanilladb.core.storage.tx.concurrency.SerializableConcurrencyMgr.modifyRecord(SerializableConcurrencyMgr.java:60)
	at org.vanilladb.core.storage.record.RecordPage.setVal(RecordPage.java:338)
	at org.vanilladb.core.storage.record.RecordPage.setVal(RecordPage.java:187)
	at org.vanilladb.core.storage.record.RecordFile.setVal(RecordFile.java:150)
	at org.vanilladb.core.query.algebra.TableScan.setVal(TableScan.java:77)
	at org.vanilladb.core.query.algebra.index.IndexSelectScan.setVal(IndexSelectScan.java:99)
	at org.vanilladb.core.query.algebra.SelectScan.setVal(SelectScan.java:70)
	at org.vanilladb.core.query.planner.index.IndexUpdatePlanner.executeModify(IndexUpdatePlanner.java:174)
	at org.vanilladb.core.query.planner.Planner.executeUpdate(Planner.java:68)
	at netdb.software.benchmark.procedure.vanilladb.SampleTxnProc.executeSql(SampleTxnProc.java:63)
	at netdb.software.benchmark.procedure.vanilladb.SampleTxnProc.execute(SampleTxnProc.java:33)
	at org.vanilladb.core.remote.storedprocedure.RemoteConnectionImpl.callStoredProc(RemoteConnectionImpl.java:32)
	at sun.reflect.GeneratedMethodAccessor1.invoke(Unknown Source)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:606)
	at sun.rmi.server.UnicastServerRef.dispatch(UnicastServerRef.java:322)
	at sun.rmi.transport.Transport$1.run(Transport.java:177)
	at sun.rmi.transport.Transport$1.run(Transport.java:174)
	at java.security.AccessController.doPrivileged(Native Method)
	at sun.rmi.transport.Transport.serviceCall(Transport.java:173)
	at sun.rmi.transport.tcp.TCPTransport.handleMessages(TCPTransport.java:556)
	at sun.rmi.transport.tcp.TCPTransport$ConnectionHandler.run0(TCPTransport.java:811)
	at sun.rmi.transport.tcp.TCPTransport$ConnectionHandler.run(TCPTransport.java:670)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1145)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:615)
	at java.lang.Thread.run(Thread.java:744)
```

該怎麼辦？


### Fetures

這個狀況的特徵是在於送出這個 exception 的位置 `concurrency.LockTable`。


### Solution

造成這個狀況的原因通常由於我們 LockTable 現在是 2 Phase Locking concurrency strategy，所以當兩個 Tx 有可能會 DeadLock 的時候他會先 Abort 等的那個 Tx 。 目前可以先忽略這個Exception 。
