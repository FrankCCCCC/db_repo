# 我開啟 Merge Request 時顯示有 Conflict 存在，要如何處理？

Fork 之後，我完成了我的作業。 當我想要開 merge request 時，出現了錯誤訊息告訴我我的 branch 跟助教的 repository 之間有 conflict。 請問該如何處理？

## 發生原因

這個狀況通常發生在 fork 了助教的 repository 之後，助教的 repository 有加上了新的 commit 所造成的。 因為助教的 repository 多了新的 commit，導致學生自己 fork 的版本沒有跟上助教的最新版。 之後可能又增加了新的內容，導致兩邊版本出現了分岐，進而出現了 conflict 的情況。

## 解決辦法

這邊提供兩種常見的解決辦法。 第一種適用於 fork 之後，自己還沒有 commit 新的版本上去，或是已經 commit 的版本不重要時使用。 第二種則適用於作業已經完成，不方便砍掉自己已經 commit 的版本時使用。

### 解法一：砍掉重練

第一種解法就是將自己 fork 的 repository 砍掉。

只要點選 fork 後的專案頁面的左側選單之中，最下方的「Settings」。 然後滑到設定頁面最下面，選擇「Remove Project」，並依照指示刪除專案。 最後重新 fork 一次就可以。

這是最快的解決方式，**缺點是會讓自己已經上傳的資料消失**。 若是想要避免這個問題，請使用第二種解法。

### 解法二：手動 merge

第二種作法是在自己的電腦上，先手動 merge 助教的最新版本，然後再上傳上去。

首先第一步要先把自己電腦上的 repository 跟助教的 repository 連結起來，也就是把它加入 remote 清單。 這點可以透過以下指令進行：

```
> git remote add [Remote Name] [URL]
```

`[Remote Name]` 可以填上任意喜歡的名稱，`[URL]` 請填上助教的 repository 的 clone URL。 注意不是自己 fork 後的 repository，而是助教的 repository。

以作業一為例，助教的 repository 是在[這個網頁](https://shwu10.cs.nthu.edu.tw/courses-cloud-databases-2017-spring/Clouddb17-Assignment-1)中，正中心的「https://shwu10.cs.nthu.edu.tw/courses-cloud-databases-2017-spring/Clouddb17-Assignment-1.git」。

假設我將 `[Remote Name]` 命名為 `tas`，該指令就是：

```
> git remote add tas https://shwu10.cs.nthu.edu.tw/courses-cloud-databases-2017-spring/Clouddb17-Assignment-1.git
```

這個時候若打 `git remote show` 應該會看到 `tas`：

```
> git remote show
origin
tas
```

`origin` 代表的就是當初 clone 時的位置，理論上應該是指向自己帳號下的 repository。

確認之後，透過 `git fetch [Remote Name] master` 可以下載助教 repository 的 master branch 上的最新資料：

```
> git fetch tas master
From shwu10.cs.nthu.edu.tw:courses-cloud-databases-2017-spring/Clouddb17-Assignment-1
 * branch            master     -> FETCH_HEAD
```

此時若用 `git branch --all` 可以看到一些助教 repository 上面的 branch：

```
> git branch --all
* master
  remotes/origin/HEAD -> origin/master
  remotes/origin/master
  remotes/origin/team-1
  ...
  remotes/origin/team-9
  remotes/tas/master
  remotes/tas/team-1
  ...
  remotes/tas/team-9
```

這個時候就要來進行手動 merge，目標是將助教的最新版跟自己的版本合併。 首先先切換到自己的最新版 (假設在 master branch)，然後合併助教的最新版：

```
> git checkout master
> git merge remotes/tas/master
```

此時若兩邊的版本有衝突 (修改到相同檔案)，那麼就會進入衝突模式。 使用 `git status` 可以看到衝突的檔案有哪些。

修改的內容就需要由學生自行判斷衝突的部分。修改完畢之後，將修改後的檔案都使用 `git add` 加入，最後 `git commit` 來確認這個版本。

這樣就完成合併的工作，之後只要 push 上去，然後再開一次 merge request 就好。
