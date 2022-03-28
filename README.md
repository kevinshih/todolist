# springboot2

這是spring boot 2.0版

Project Name: todolist

Environment: java, spring boot, gradle, mysql, postman, win10, jdk1.8

Import schema and data: “ todolist.sql “

The todolist.war is in path “ D:\git\todolist\build\libs\todolist.war ”

Command:
D:\git\todolist>java -jar build/libs/todolist.war

Introduce API:
**List** contains muliple **Item**. Item can only belong to one List.
Time complexity : O(N)
	user can be show all his lists content and list name is order by list’s duedate, and each list’s item is sort by sortid.
Get: http://localhost:9003/todolist/api/getMyAllListAndItemByUserid/?userid=0


**List** belongs to one **User** but can be shared with other **User**
Time complexity : O(1)
Post: http://localhost:9003/todolist/api/shareListToOtherUsers
{
"name":"projectB",
"listid":1,
"userid":1,
"duedate":"2022-03-31 00:00:00"
}
Orignally, userid 1 has list project C, project D. 
After executing the post api. Let project B share to userid 1.


**Item** can be moved between different or within the same **List** (ordering matters)
Time complexity : O(N)
Get http://localhost:9003/todolist/api/moveItem/?oriListid=0&oriSortid=1&targetListid=1&targetSortid=2&direction=down
Beside the corner cases, there are almost 10 cases when we move one item in a list to a point we assigned.
There are 6 cases when move one item in the same list.
There are 4 cases when move one item to a different list.


**List** should have a due date that can be changed
Time complexity : O(N)
Get http://localhost:9003/todolist/api/updateListDuedate
Originally, projectA’s duedate is 3/31.
After adjustment the projectA’s duedate.