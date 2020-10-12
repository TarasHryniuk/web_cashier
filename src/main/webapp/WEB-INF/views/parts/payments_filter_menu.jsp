<%--
  Created by IntelliJ IDEA.
  User: tarashryniuk
  Date: 11.10.2020
  Time: 18:54
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/views/parts/include.jsp" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="resources/css/bootstrap/bootstrap.css" media="screen"/>
    <link rel="stylesheet" type="text/css" href="resources/css/custom.css" media="screen"/>
    <link rel="stylesheet" type="text/css" href="resources/css/datepicker.css" media="screen"/>

</head>
<body>

<div class="container">
    <div class='col-md-5'>
        <div class="form-group">
            <div class='input-group date' id='datetimepicker6'>
                <input type='text' class="form-control" />
                <span class="input-group-addon">
            <span class="glyphicon glyphicon-calendar"></span>
            </span>
            </div>
        </div>
    </div>
    <div class='col-md-5'>
        <div class="form-group">
            <div class='input-group date' id='datetimepicker7'>
                <input type='text' class="form-control" />
                <span class="input-group-addon">
            <span class="glyphicon glyphicon-calendar"></span>
            </span>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function () {
        $('#datetimepicker6').datetimepicker();
        $('#datetimepicker7').datetimepicker({
            useCurrent: false //Important! See issue #1075
        });
        $("#datetimepicker6").on("dp.change", function (e) {
            $('#datetimepicker7').data("DateTimePicker").minDate(e.date);
        });
        $("#datetimepicker7").on("dp.change", function (e) {
            $('#datetimepicker6').data("DateTimePicker").maxDate(e.date);
        });
    });
</script>

<div class="nav justify-content-center">
    <div id="navigation" class="nav-item">

        <ul id="main-nav" class="nav-item">
            <ul class="nav-item">
                <form id="initialize.work.station" action="controller" method="post">
                    <input type="hidden" name="command" value="initialize.work.station"/>
                    <input type="date" name="calendar" class="form-control">
                    <input class="btn btn-link" type="submit" value="<fmt:message key="search"/>"/>
                </form>
            </ul>
        </ul>
    </div>
</div>

<script type="text/javascript" src="resources/js/jquery-3.5.1.min.js"></script>
<script type="text/javascript" src="resources/js/bootstrap/bootstrap.min.js"></script>

</body>
</html>

