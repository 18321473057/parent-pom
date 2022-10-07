



[^Issue匹配规则]: 



| 操作工具 | issue         | branch     | commit                                      | 结果                                                         |
| -------- | ------------- | ---------- | ------------------------------------------- | ------------------------------------------------------------ |
| Jira     | HRSSC-88      | HRSSC-88-1 | ""                                          | 只有HRSSC-88 可以看到   ，如果不设置，浏览中也无超链接       |
| Jira     | HRSSC-88      | HRSSC-88-1 | HRSSC-88                                    | 只有HRSSC-88 可以看到， 提交记录被覆盖，浏览中显示HRSSC-88的超链接 |
| Jira     | HRSSC-88      | HRSSC-88-2 | HRSSC-85                                    | HRSSC-88/HRSSC-85都看得见，浏览中显示HRSSC-85的超链接        |
| Jira     | HRSSC-88      | HRSSC-88-2 | 再次提交 HRSSC-88                           | 提交记录被覆盖，浏览中显示HRSSC-88的超链接,但是HRSSC-85 依然可见 |
| Jira     | HRSSC-88      | HRSSC-8    | ""                                          | HRSSC-88/HRSSC-85均不可见  ，HRSSC-8可见                     |
| Jira     | HRSSC-88      | ABCD-1     | ""                                          | 均不可见                                                     |
| Jira     | HRSSC-88      | ABCD-1     | 再次提交 HRSSC-88                           | HRSSC-88 可见                                                |
| Idea     | ------------- | HRSSC-88-3 | ""                                          | HRSSC-88 可见                                                |
| Idea     | ------------- | HRSSC-88-3 | HRSSC-70                                    | HRSSC-88/HRSSC-70  均可见可见, 浏览中的显示HRSSC-70超链      |
| Idea     | ------------- | HRSSC-88-3 | HRSSC-70    idea提交分支HRSSC-88 标HRSSC-70 | 会出现HRSSC-70 ，HRSSC-88两个超链，可见只要与issue 相同即可  |

- branch 包含issue则匹配成功
- commit 包含 issue 则匹配成功

- Issue一旦见过branch 就不会再被隐藏

- 浏览中的超链接，依赖commit 的提交内容且可以被修改 （规则 以： issue  开头）



