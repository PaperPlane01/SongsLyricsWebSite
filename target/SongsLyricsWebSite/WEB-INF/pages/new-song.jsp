<%--
  Created by IntelliJ IDEA.
  User: Администратор
  Date: 02.08.2017
  Time: 20:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add new song</title>
    <script src="/scripts/jquery-3.2.1.js"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="/css/body.css">
    <link rel="stylesheet" href="/css/menu.css">
</head>
<body>
<div class="container">
<div class="row">
    <div class="col-xs-12">
        <jsp:include page="menu.jsp"></jsp:include>
    </div>
</div>
    <div class="row">
        <div class="col-xs-12">
            <form name="addNewSong" method="post" action="/controller?command=addsong">
                Song artist:<br>
                <input type="text" name="songArtist"/><br>
                Song featured artists:<br>
                <input type="text" name="songFeaturedArtists"/><br>
                Song name:<br>
                <input type="text" name="songName"/><br>
                Song genres:<br>
                <input type="text" name="songGenres"/><br>
                YouTube link:<br>
                <input type="text" name="youTubeLink"/>
                Song lyrics:<br>
                <textarea name="songLyrics"></textarea><br>
                <input type="submit" value="Submit"/><br>
            </form>
        </div>
    </div>
</div>
</body>
</html>
