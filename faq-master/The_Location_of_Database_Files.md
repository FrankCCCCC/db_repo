# The Location of Database Files
### Question

請問今天我開啟了一個 database 之後，這個 database 的 files 會存在哪裡？

### Answer

VanillaDB 預設會在你所使用的系統之中，user 所屬的 home 資料夾下開一個與資料庫名稱相同的資料夾。
假設你的 database 命名為 `meow_db`，使用者名稱為 `cat`的話。在不同系統下通常會在這些位置：

- Windows
	- English
		- `C:\Users\cat\meow_db`
	- Traditional Chinese
		- `C:\使用者\cat\meow_db`
- Linux
	- `\home\cat\meow_db`
- MacOS
	- `\Users\cat\meow_db`

另外，你可以透過修改 vanilladb.properties 之中的設定，變更 database files 存放的位置：

```properties
org.vanilladb.core.storage.file.FileMgr.HOME_DIR=
```

例如我想把我的 database 資料夾放在 Windows 中的 D 槽：

```properties
org.vanilladb.core.storage.file.FileMgr.HOME_DIR=D:\
```

或者我是 MacOS，我想放在桌面上：

```properties
org.vanilladb.core.storage.file.FileMgr.HOME_DIR=\Users\cat\Desktop
```

然後再重新啟動 database 即可。
