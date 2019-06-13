package lotto;

import lotto.controller.*;
import lotto.dao.LottoDao;
import lotto.dao.RoundDao;
import lotto.dao.WinPrizeDao;
import lotto.dao.WinningLottoDao;
import lotto.service.LottoService;
import lotto.service.RoundService;
import lotto.service.WinPrizeService;
import lotto.service.WinningLottoService;
import lotto.utils.Encoder;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import static spark.Spark.*;

public class WebUILottoApplication {

    public static void main(String[] args) {
        port(8080);

        RoundDao roundDao = new RoundDao();
        LottoDao lottoDao = new LottoDao();
        WinPrizeDao winPrizeDao = new WinPrizeDao();
        WinningLottoDao winningLottoDao = new WinningLottoDao();

        RoundService roundService = new RoundService(roundDao);
        LottoService lottoService = new LottoService(lottoDao);
        WinPrizeService winPrizeService = new WinPrizeService(winPrizeDao);
        WinningLottoService winningLottoService = new WinningLottoService(winningLottoDao);

        LottoController lottoController = new LottoController(roundService, lottoService);
        ResultController resultController = new ResultController(winPrizeService, winningLottoService, lottoService);
        ErrorController errorController = new ErrorController();
        MainController mainController = new MainController(roundDao);

        externalStaticFileLocation("src/main/resources/templates");

        get("/", mainController::main);

        get("/round", RoundController::round);

        post("/lottos", lottoController::addLottos);

        get("/lottos", lottoController::getLottos);

        post("/result", resultController::doPostResult);

        get("/result", resultController::doGetResult);

        get("/error", errorController::exception);

        exception(Exception.class, (exception, req, res) -> {
            String message = null;
            try {
                message = Encoder.encodeUTF8(exception.getMessage());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            res.redirect("/error?message=" + message);
        });
    }

    public static String render(Map<String, Object> model, String templatePath) {
        return new HandlebarsTemplateEngine().render(new ModelAndView(model, templatePath));
    }
}
