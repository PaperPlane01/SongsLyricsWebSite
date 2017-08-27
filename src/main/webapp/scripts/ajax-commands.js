function doLogin(username, password) {
    $.post(
        {
            url : 'controller',
            data : {
                command : "login",
                username : username,
                password : password,
            },

            success : function(responseData) {
                if (responseData != null) {
                    showLoginResult(responseData);
                    $("#message").css("display","block");
                } else{
                    $('#message').css("display","none");
                    $('#message').html("");
                    alert("Some exception occurred! Please try again.");
                }
            }
        }
    )
}

function doLogout() {
    $.post(
        {
            url : 'controller',
            data : {
                command: "logout"
            },

            success : function () {
                location.reload();
            }
        }
    )
}

function loadArtistLetters() {
    $.post(
        {
            url : 'controller',
            data : {
                command : "artistsletters"
            },
            
            success : function (responseData) {
                displayArtistsLetters(responseData);
            }
        }
    )
}

function loadArtists(letter) {
    $.post(
        {
            url : 'controller',
            data : {
                command : "artists",
                letter : letter
            },

            success : function (responseData) {
                displayArtists(responseData);
            }
        }
    )
}

function loadSongsOfArtist(artistName) {
    $.post(
        {
            url : 'controller',
            data : {
                command : "songs",
                artistName : artistName
            },

            success : function (responseData) {
                displaySongs(responseData);
            }
        }
    )
}

function changeLanguage(locale) {
    $.post(
        {
            url : 'controller',
            data : {
                command : "changelanguage",
                language : locale
            },

            success : function () {
                PageGlobals.currentLocale = locale;
                location.reload();
            }
        }
    )
}

function getLabel(labelKey, locale) {

    var labelContent = "";

    $.ajax(
        {
            url : 'controller',
            async : false,
            method : "post",
            data : {
                command : 'getlabel',
                labelKey : labelKey,
                locale : locale
            },

            success : function (responseLabel) {
                console.log(responseLabel);
                labelContent = responseLabel;
            }
        }
    );

    return labelContent;
}


