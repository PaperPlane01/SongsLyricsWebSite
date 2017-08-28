package kz.javalab.songslyricswebsite.command;

import kz.javalab.songslyricswebsite.command.localebasedcommand.AddSongCommand;
import kz.javalab.songslyricswebsite.command.localebasedcommand.GetLabelCommand;
import kz.javalab.songslyricswebsite.command.localebasedcommand.LoginCommand;

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
    },

    PROFILE {
        {
            this.command = new ProfileCommand();
        }
    },

    GETLABEL {
        {
            this.command = new GetLabelCommand();
        }
    };

   ActionCommand command;

    public ActionCommand getCurrentCommand() {
        return command;
    }
}
