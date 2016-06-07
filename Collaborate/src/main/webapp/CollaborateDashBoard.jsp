<%@ page session="false"%>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Dashboard</title>
<script type="text/javascript"
	src="http://cdnjs.cloudflare.com/ajax/libs/json2/20110223/json2.min.js"></script>
<script type="text/javascript" src="resources/jquery/jq.js"></script>
<script type="text/javascript" src="utils/URLMapping.js"></script>
<script type="text/javascript" src="utils/AjaxUtils.js"></script>
<script type="text/javascript" src="resources/ext-all.js"></script>
<script type="text/javascript" src="apps/Collaborate.js"></script>
<script type="text/javascript" src="resources/ext-theme-neptune.js"></script>
<link rel="stylesheet" href="resources/css/ext-all-neptune.css" />
<link rel="stylesheet" href="resources/css/bootstrap.min.css" />
<link rel="stylesheet" href="resources/css/Collaborate.css" />

<script type="text/javascript">
	var doLogout = function() {
		CollaborateUtil.doLogout({
			logout : true
		}, function(RESP) {

			if (RESP.status_code === '00') {
				window.location = RESP.loginUrl;
			} else {
				Ext.Msg.show({
					title : 'Collaborate',
					msg : RESP.status_msg,
					icon : Ext.Msg.ERROR
				});
			}
		});
	}
</script>

</head>
<body onload="renderCollaborate('collaborateView')">

	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="#">Collaborate</a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav navbar-right">
					<li><a href="#" onclick="doLogout()">Sign Out <span
							class="glyphicon glyphicon-log-out" aria-hidden="true" /></a></li>
				</ul>
			</div>
		</div>
	</nav>


	<div class="container-fluid">
		<div class="row">
			<div class="col-md-2 sidebar">
				<ul class="nav nav-sidebar">
					<li class="active"><a href="#">Overview</a></li>
					<li><a href="#">Reports</a></li>
					<li><a href="#">Analytics</a></li>
					<li><a href="#">Export</a></li>
				</ul>
			</div>
			<div class="col-md-10 main" id="collaborateView" />
		</div>
	</div>
</body>
</html>
