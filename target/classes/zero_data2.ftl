<html lang="en">
<head>
	<meta charset="UTF-8">
	<title align="center">WPS报表数据监控-${date}</title>
	<style type="text/css">
table.dataintable {
	margin-top:15px;
	border-collapse:collapse;
	border:1px solid #aaa;
	width:100%;
	}

table.dataintable th {
	vertical-align:baseline;
	padding:5px 15px 5px 6px;
	background-color:#3F3F3F;
	border:1px solid #3F3F3F;
	text-align:left;
	color:#fff;
	}

table.dataintable td {
	vertical-align:text-top;
	padding:6px 15px 6px 6px;
	border:1px solid #aaa;
	}

table.dataintable tr:nth-child(odd) {
	background-color:#F5F5F5;
}

table.dataintable tr:nth-child(even) {
	background-color:#fff;
}
	</style>
</head>
<body>
 <h1 align="center">WPS报表数据监控——${date}</h1>
<table class="dataintable">
 <tr><th width="20%">KingSight报表名称</th><th width="60%">问题字段</th><th width="20%">异常原因描述</th></tr>

  <#list list as data>
     <tr>
    <td align="center" valign="center"><i>${(data.reportComment)!}<i></td>
            <td>
                ${(data.reportDetail)!}
           </td>
            <td align="center" valign="center"><i>${(data.reason)!}</i></td>
   </tr>
  </#list>
</table>
</body>
</html>