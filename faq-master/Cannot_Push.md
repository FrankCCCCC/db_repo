# 為什麼我一直 Push 失敗？

## 檢查問題

先檢查一下 push 錯誤時顯示的訊息 (請參考[附圖](Cannot_Push_Figure.png))，push 的網址 (附圖中紅線部分) 是不是你自己的 repository。

如果你看到的網址是 `http://shwu10.cs.nthu.edu.tw/courses/databases/2019-spring/...`，而不是 `http://shwu10.cs.nthu.edu.tw/[你的 Gitlab 帳號]/...`，那就代表你 clone 的時候，從錯誤的地方 clone 下來。

## 正確的程序

一般來說，應該要先透過 fork 把助教提供的作業複製到自己的帳號下，然後再透過 clone 把自己帳號下的作業下載下來。

Git 會自動記錄 clone 下來時的位置，並記錄在 remote 裡 (預設會綁定在 origin 這個名稱上)。如果一開始下載的時候是從助教的帳號下載，那麼 push 的時候 git 就會嘗試上傳到助教的 repository。而學生沒有權限直接上傳到助教的空間，因此當然就 push 失敗囉！

## 解決辦法

解決辦法就是使用下列指令，將上傳的位置從原本 clone 下來的位置綁定到自己帳號下的 repository 就好。

```
> git remote set-url origin [你帳號下的 repository 網址]
```

假設我的帳號是 `123456789`，然後我 fork 了助教的 assignment 1 repository，所以此時我帳號下應該有個 repository 在 `http://shwu10.cs.nthu.edu.tw/123456789/CloudDB17-Assignment-1`。

然後我不小心 clone 錯了，所以修正指令就是：

```
> git remote set-url origin http://shwu10.cs.nthu.edu.tw/123456789/CloudDB16-Assignment-1
```

可以透過 `git remote show origin` 檢查 `Push URL` 有沒有設定成功：

```
> git remote show origin
* remote origin
  Fetch URL: http://shwu10.cs.nthu.edu.tw/123456789/CloudDB17-Assignment-1
  Push  URL: http://shwu10.cs.nthu.edu.tw/123456789/CloudDB17-Assignment-1
  HEAD branch: master
  Remote branch:
    master tracked
  Local branch configured for 'git pull':
    master merges with remote master
  Local ref configured for 'git push':
    master pushes to master (up to date)
```

修正之後重新 push 即可。

## 還是沒有解決

如果你不是因為 clone 錯誤，或者換了位址之後還是失敗的話。請盡速聯絡助教。
