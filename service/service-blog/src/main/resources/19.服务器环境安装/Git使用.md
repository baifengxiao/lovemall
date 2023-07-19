## Git使用

**1、创建全局配置**

如果你刚开始使用 Git，或许你还没有该文件。不用担心，让我们直接开始。只需要用 --edit 选项：

```
$ git config --global --edit
```

如果没有该文件，Git 将会创建一个包含以下内容的新文件，并使用你终端的默认编辑器打开它：

```
# This is Git's per-user configuration file.

[user]

        email = javaer@111.com

        name = pengtaoyu

[core]

        editor = vim

[init]

        defaultBranch = main
~                                   
```

现在我们已经打开了编辑器，并且 Git 已经在后台创建了全局配置文件，我们可以继续接下来的设置。

**2、设置默认名称**

**3、设置默认邮箱地址**。

**4、设置默认分支名称**

**5、设置默认编辑器**

第五个设置是设置默认的编辑器。这是指 Git 将使用的编辑器，用于在你每次将更改提交到存储库时输入你的提交消息。不论是 nano、emacs、vi 还是其他编辑器，每个人都有他喜欢的。

```
[core]

            editor = vim
```

这是最后一项。退出编辑器。Git 在主目录下保存全局配置文件。如果你再次运行编辑命令，将会看到所有内容。注意配置文件是明文存储的文本文件，因此它可以很容易使用文本工具查看，如 cat 命令。这是我的配置文件内容：

```
$ cat ~/.gitconfig

# This is Git's per-user configuration file.

[user]

        email = javaer@111.com

        name = pengtaoyu

[core]

        editor = vim

[init]

        defaultBranch = main

```

