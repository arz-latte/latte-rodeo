<!DOCTYPE HTML>
<html>
<head>
<title>latte rodeo</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylsheet" href="${pageContext.request.contextPath}/css/rodeo.css"></link>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/require.js"></script>
<script type="text/javascript">
	requirejs(['${pageContext.request.contextPath}/app/rodeo.js'], function(Rodeo){
		require(['app/index']);		
	})
</script>
</head>
<body>
</body>
</html>