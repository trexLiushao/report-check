<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>KingSight报表数据异常情况</title>
</head>
<body>
    <h1>WPS报表数据监控——${date}</h1>
    <table>
    <tr><td align="center">中文表名</td><td align="center">问题字段</td><td align="center">异常原因描述</td></tr>
    <#list list as data>
    <tr>
            <td>${(data.reportComment)!}</td>
            <td>
            <#list data.zeroColumnList as detail>
            <th>${(detail)}</th>
            </#list>
            </td>
            <td>${(data.reason)!}</td>
            </tr>
    </#list>
    </table>
</body>
</html>