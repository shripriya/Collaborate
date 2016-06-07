<%@ page session="false"%>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Collaborate Login</title>
<script type="text/javascript"
	src="http://cdnjs.cloudflare.com/ajax/libs/json2/20110223/json2.min.js"></script>
<script type="text/javascript" src="resources/jquery/jq.js"></script>
<script type="text/javascript" src="utils/URLMapping.js"></script>
<script type="text/javascript" src="utils/AjaxUtils.js"></script>
<script type="text/javascript" src="resources/ext-all.js"></script>
<script type="text/javascript" src="resources/ext-theme-neptune.js"></script>
<script type="text/javascript" src="apps/Login.js"></script>
<link rel="stylesheet" href="resources/css/bootstrap.min.css" />
<link rel="stylesheet" href="resources/css/ext-all-neptune.css" />
<link rel="stylesheet" href="resources/css/Collaborate.css" />
</head>
<body id="appBody" onload="renderLogin('mainContainer')">
	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="#">Collaborate</a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav navbar-right">
					<li><a href="/Collaborate">Login</a></li>
					<li><a href="#" onclick="">Sign Up</a></li>
					<li><a href="#">Verify Email</a></li>
				</ul>
			</div>
		</div>
	</nav>

	<div class="row">
		<div class="container-fluid">
			<div class="col-lg-12" id="mainContainer"></div>
		</div>
	</div>

</body>
</html>
