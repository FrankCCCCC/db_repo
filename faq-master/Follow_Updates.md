# 追蹤助教的更新

這篇文章解釋了如何將已經 fork 的 repository 跟助教的 repository 同步，以更新到最新版本。

## 作法一：Delete & Re-fork

這個做法比較暴力，但是比較快。缺點是之前自己已經修改的紀錄就會不見。

1. 將自己已經修改的部份用 git 以外的方式記錄下來。(記事本等等)
2. 到 GitLab 找到自己的 repository。
3. 到 repository 的 `Settings` > `General` > `Advanced settings` > `Remove project` 刪除 repository。
4. 重新到助教的 repository fork 一遍，並 clone 下來。
5. 把修改的部分填回去。
6. 完成！

## 作法二：Merge Upstream

這個方法屬於比較正統的做法。一般來說若參與網路上的 Open Source Project，也會使用這個做法。

1. 將助教的 repository 的 clone 網址複製下來。例如，作業一的 clone URL 是：`https://shwu10.cs.nthu.edu.tw/courses-databases-2018-spring/db18-assignment-1.git`
2. 找到自己電腦中的 repository，並在該處開啟 command line 的介面。
3. 使用 `git remote add [名稱] [URL]` 將助教的 repository 加入 remote 清單中。例如將作業一的 URL 以 `tas` 的名稱放入：

    ```bash
    > git remote add tas https://shwu10.cs.nthu.edu.tw/courses-databases-2018-spring/db18-assignment-1.git
    ```
4. 下載助教 repository 的資料，假設 remote 命名為 `tas`：

    ```bash
    > git fetch tas
    ```
5. 切換到你寫作業的 branch，通常是 master (若已經在 master 就不需要切換):

    ```bash
    > git checkout master
    ```
6. 將助教的更新與自己的程式碼合併：

    ```bash
    > git merge tas/master
    ```
7. 如果沒有看到 `error` 或者 `conflict` 的話，代表沒有甚麼問題。若出現的話，請自行嘗試解決 conflict 或者採用作法一。
