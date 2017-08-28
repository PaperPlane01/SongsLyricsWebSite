package kz.javalab.songslyricswebsite.command.localebasedcommand;

import com.google.gson.Gson;
import kz.javalab.songslyricswebsite.command.ActionCommand;
import kz.javalab.songslyricswebsite.command.localebasedcommand.LocaleBasedCommand;
import kz.javalab.songslyricswebsite.exception.LyricsParsingException;
import kz.javalab.songslyricswebsite.exception.SongAddingException;
import kz.javalab.songslyricswebsite.entity.song.Song;
import kz.javalab.songslyricswebsite.entity.artist.Artist;
import kz.javalab.songslyricswebsite.entity.lyrics.SongLyrics;
import kz.javalab.songslyricswebsite.exception.SuchSongAlreadyExistsException;
import kz.javalab.songslyricswebsite.resource.ConfigurationManager;
import kz.javalab.songslyricswebsite.service.SongReader;
import kz.javalab.songslyricswebsite.service.SongsService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

/**
 * Created by PaperPlane on 19.08.2017.
 */
public class AddSongCommand extends LocaleBasedCommand implements ActionCommand {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SongsService songsService = new SongsService();
        SongReader songReader = new SongReader();
        Song song = new Song();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("labels", getLocaleFromRequest(request));
        String message = new String();

        String songName = request.getParameter("songName");
        String artistName = request.getParameter("songArtist");
        String songFeaturedArtists = request.getParameter("songFeaturedArtists");
        String songGenres = request.getParameter("songGenres");
        String songLyricsAsString = request.getParameter("songLyrics");
        String youTubeLink = request.getParameter("youTubeLink");

        if (validateSongName(songName)) {
            if (validateArtistName(artistName)) {
                if (validateFeaturedArtists(songFeaturedArtists)) {
                    if (validateSongGenres(songGenres)) {
                        if (validateSongLyrics(songLyricsAsString)) {
                            if (validateYouTubeVideoID(youTubeLink)) {
                                song.setName(songName);
                                song.setArtist(new Artist(artistName));

                                StringTokenizer stringTokenizer = new StringTokenizer(songFeaturedArtists, ";");
                                List<Artist> featuredArtists = new ArrayList<>();

                                while (stringTokenizer.hasMoreTokens()) {
                                    featuredArtists.add(new Artist(stringTokenizer.nextToken()));
                                }

                                song.setFeaturedArtists(featuredArtists);

                                List<String> genres = new ArrayList<>();

                                while (stringTokenizer.hasMoreTokens()) {
                                    genres.add(stringTokenizer.nextToken());
                                }

                                song.setGenres(genres);

                                SongLyrics songLyrics = null;

                                try {
                                    songLyrics = songReader.buildLyricsFromString(songLyricsAsString);
                                    song.setLyrics(songLyrics);

                                    String page = new String();

                                    try {
                                        songsService.addSongToDatabase(song, youTubeLink);
                                        page = ConfigurationManager.getProperty("path.page.songhasbeenadded");
                                        request.getRequestDispatcher(page).forward(request, response);
                                    } catch (SongAddingException e) {
                                        e.printStackTrace();
                                        message = resourceBundle.getString("labels.errors.errorwhileadding");
                                        sendJsonMessage(message, request, response);
                                    } catch (SuchSongAlreadyExistsException e) {
                                        message = resourceBundle.getString("labels.suchsongalreadyexists");
                                        sendJsonMessage(message, request, response);
                                    }
                                } catch (LyricsParsingException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                message = resourceBundle.getString("labels.errors.songyoutubevideoidvalidation");
                                sendJsonMessage(message, request, response);
                            }
                        } else {
                            message = resourceBundle.getString("labels.errors.songlyricsvalidation");
                            sendJsonMessage(message, request, response);
                        }
                    } else {
                        message = resourceBundle.getString("labels.errors.songgenresvalidation");
                        sendJsonMessage(message, request, response);
                    }
                } else {
                    message = resourceBundle.getString("labels.errors.featuredartistsvalidation");
                    sendJsonMessage(message, request, response);
                }
            } else {
                message = resourceBundle.getString("labels.errors.artistnamevalidation");
                sendJsonMessage(message, request, response);
            }
        } else {
            message = resourceBundle.getString("labels.errors.songnamevalidation");
            sendJsonMessage(message, request, response);
        }


    }

    private void sendJsonMessage(String message, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String json = new Gson().toJson(message);

        response.setContentType("application/json");
        response.getWriter().write(json);
    }

    private boolean validateSongName(String songName) {
        int maxSize = 100;
        boolean result = false;

        if (songName.length() < maxSize) {
            result = true;
        }

        return result;
    }

    private boolean validateArtistName(String artistName) {
        int maxSize = 50;
        boolean result = false;

        if (artistName.length() < maxSize) {
            result = true;
        }

        return result;
    }

    private boolean validateFeaturedArtists(String featuredArtists) {
        int maxSize = 250;
        boolean result = false;

        if (featuredArtists.length() < maxSize) {
            result = true;
        }

        return result;
    }

    private boolean validateSongGenres(String songGenres) {
        int maxSize = 130;
        boolean result = false;

        if (songGenres.length() < maxSize) {
            result = true;
        }

        return result;
    }

    private boolean validateYouTubeVideoID(String youTubeVideoID) {
        int maxSize = 15;
        boolean result = false;

        if (youTubeVideoID == null) {
            result = true;
            return result;
        }

        if (youTubeVideoID.length() < maxSize) {
            result = true;
        }

        return  result;
    }

    private boolean validateSongLyrics(String songLyricsAsString) {
        boolean result = false;
        int maxSize = 5000;

        System.out.println(songLyricsAsString.length());

        if (songLyricsAsString.length() > maxSize) {
            result = false;
            return  result;
        }

        SongReader songReader = new SongReader();

        try {
            SongLyrics songLyrics = songReader.buildLyricsFromString(songLyricsAsString);
            result = true;
            return result;
        } catch (LyricsParsingException e) {
            e.printStackTrace();
            result = false;
            return  result;
        }
    }
}
