# Assignment 3

## FAQ

### Question 1: `BufferAbortException` 是甚麼
> M 君: 為什麼我跑實驗的時候冒出一個 `BufferAbortException`？

`BufferAbortException` 發生於 tx 在 buffer pool 裡面請求 buffer 被駁回的時候。 最常見的原因就是同一個 tx 使用了大量的 buffer，超過 buffer pool 能夠容納的上限。 Buffer pool 是管理暫存在記憶體內的 disk 資料。因此 buffer pool 用盡就是記憶體用盡的意思。 因此只需要到 `vanilladb.properties` 內把 buffer pool 的上限提高就可以解決這個問題：

```properties
# The size of buffer pool.
org.vanilladb.core.storage.buffer.BufferMgr.BUFFER_POOL_SIZE=102400
```

要注意的是，`vanilladb.properties` 記得修改使用的 project 裡的那份！ 如果是從 `core-patch` 啟動就改 `core-patch` 的 `vanilladb.properties`，從 `bench` 啟動就改 `bench` 的 `vanilladb.properties`。