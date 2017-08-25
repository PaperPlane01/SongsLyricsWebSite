package kz.javalab.songslyricswebsite.command;

/**
 * Created by PaperPlane on 07.08.2017.
 */
public enum CommandEnum {
    SONGS {
        {
            this.command = new ListOfSongsCommand();
        }
    },

    SONG {
        {
            this.command = new SongCommand();
        }
    },

    REGISTER {
        {
            this.command = new RegisterCommand();
        }
    },

    REGISTRATION {
        {
            this.command = new RegistrationCommand();
        }
    },

    LOGIN {
        {
            this.command = new LoginCommand();
        }
    },

    MYPROFILE {
        {
            this.command = new MyProfileCommand();
        }
    },

    LOGOUT {
        {
            this.command = new LogOutCommand();
        }
    },

    NEWSONG {
        {
            this.command = new NewSongCommand();
        }
    },

    ADDSONG {
        {
            this.command = new AddSongCommand();
        }
    },

    APPROVESONG {
        {
            this.command = new ApproveSongCommand();
        }
    },

    NOTAPPROVEDSONGS {
        {
            this.command = new NotApprovedSongsCommand();
        }
    },

    ARTISTSLETTERS {
        {
            this.command = new ArtistsLettersCommand();
        }
    },

    ARTISTS {
        {
            this.command = new ArtistsCommand();
        }
    },

    CHANGELANGUAGE {
        {
            this.command = new ChangeLanguageCommand();
        }
    };

   ActionCommand command;

    public ActionCommand getCurrentCommand() {
        return command;
    }
}
