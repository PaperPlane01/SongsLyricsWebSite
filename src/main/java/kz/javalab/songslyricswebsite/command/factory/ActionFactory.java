package kz.javalab.songslyricswebsite.command.factory;

import kz.javalab.songslyricswebsite.command.ActionCommand;
import kz.javalab.songslyricswebsite.command.impl.*;
import kz.javalab.songslyricswebsite.command.impl.jsoncommand.*;
import kz.javalab.songslyricswebsite.command.impl.localebasedcommand.*;
import kz.javalab.songslyricswebsite.constant.RequestConstants;

import javax.servlet.http.HttpServletRequest;

/**
 * <Code>ActionFactory</Code> class is responsible for defining next action to be performed.
 * The next action depends on "command" attribute of request.
 */
public class ActionFactory {

    public ActionFactory() {
    }

    /**
     * Defines next action to be performed.
     * The next action depends on "command" attribute of request.
     * @param request Request to be handled.
     * @return Appropriate <Code>ActionCommand</Code> instance, which is based on "command" attribute of request.
     */
    public ActionCommand defineCommand(HttpServletRequest request) {
        ActionCommand command = new EmptyCommand();
        String action = request.getParameter(RequestConstants.RequestParameters.COMMAND);

        System.out.println(action);

        if (action == null || action.isEmpty()) {
            return command;
        }

        switch (action) {
            case RequestConstants.Commands.ADD_COMMENT:
                command = new AddCommentCommand();
                break;
            case RequestConstants.Commands.ADD_SONG:
                command = new AddSongCommand();
                break;
            case RequestConstants.Commands.APPLY_SONG_CHANGES:
                command = new ApplySongChangesCommand();
                break;
            case RequestConstants.Commands.APPROVE_SONG:
                command = new ApproveSongCommand();
                break;
            case RequestConstants.Commands.ARTISTS:
                command = new ArtistsCommand();
                break;
            case RequestConstants.Commands.ARTISTS_LETTERS:
                command = new ArtistsLettersCommand();
                break;
            case RequestConstants.Commands.BLOCK_USER:
                command = new BlockUserCommand();
                break;
            case RequestConstants.Commands.CHANGE_LANGUAGE:
                command = new ChangeLanguageCommand();
                break;
            case RequestConstants.Commands.CHANGE_PASSWORD:
                command = new ChangePasswordCommand();
                break;
            case RequestConstants.Commands.DELETE_COMMENT:
                command = new DeleteCommentCommand();
                break;
            case RequestConstants.Commands.EDIT_SONG:
                command = new EditSongCommand();
                break;
            case RequestConstants.Commands.GET_LABEL:
                command = new GetLabelCommand();
                break;
            case RequestConstants.Commands.LOG_OUT:
                command = new LogOutCommand();
                break;
            case RequestConstants.Commands.LOGIN:
                command = new LoginCommand();
                break;
            case RequestConstants.Commands.NEW_SONG:
                command = new NewSongCommand();
                break;
            case RequestConstants.Commands.NOT_APPROVED_SONGS:
                command = new NotApprovedSongsCommand();
                break;
            case RequestConstants.Commands.PROFILE:
                command = new ProfileCommand();
                break;
            case RequestConstants.Commands.RATE_SONG:
                command = new RateSongCommand();
                break;
            case RequestConstants.Commands.RECENTLY_ADDED_SONGS:
                command = new RecentlyAddedSongsCommand();
                break;
            case RequestConstants.Commands.REGISTER:
                command = new RegisterCommand();
                break;
            case RequestConstants.Commands.SONG:
                command = new SongCommand();
                break;
            case RequestConstants.Commands.SONGS:
                command = new ListOfSongsCommand();
                break;
            case RequestConstants.Commands.TOP_TEN_RATED_SONGS:
                command = new TopTenRatedSongsCommand();
                break;
            case RequestConstants.Commands.UNBLOCK_USER:
                command = new UnblockUserCommand();
                break;
            default:
                command = new EmptyCommand();
                break;
        }

        return command;
    }
}
