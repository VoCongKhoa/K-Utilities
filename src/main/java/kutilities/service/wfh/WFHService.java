package kutilities.service.wfh;

import com.fasterxml.jackson.databind.ObjectMapper;
import kutilities.domain.constants.wfh.WFHConstants;
import kutilities.domain.dto.wfh.GetAllFlashCardsRequest;
import kutilities.domain.dto.wfh.BackValue;
import kutilities.domain.dto.wfh.CharacterDTO;
import kutilities.domain.dto.wfh.FrontValue;
import kutilities.domain.entity.wfh.FlashCardEntity;
import kutilities.domain.entity.wfh.FlashCardXMLEntity;
import kutilities.repository.wfh.FlashCardEntityRepository;
import kutilities.service.excel.ExcelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static kutilities.utils.ExcelUtils.getCellValue;

@Service
@RequiredArgsConstructor
@Slf4j
public class WFHService {
    public final ExcelService excelService;
    public final XMLService xmlService;
    public final FlashCardEntityRepository flashCardEntityRepository;


    //        @Transactional
    // Read file BRS_HFW_Manip_Kit_Interactive_Flash_Cards_v_1
    public List<FlashCardEntity> getAllFlashCardsAsList(HttpServletResponse response, GetAllFlashCardsRequest request) {
        List<FlashCardEntity> flashCards = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try (FileInputStream fis = new FileInputStream(request.getFilePathExcel());
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {
            XSSFSheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == WFHConstants.NO) {
                    continue;
                }
                FlashCardEntity.FlashCardEntityBuilder flashCard = FlashCardEntity.builder();
                FrontValue.FrontValueBuilder frontValue = FrontValue.builder();
                BackValue.BackValueBuilder backValue = BackValue.builder();
                for (Cell cell : row) {
                    String cellValue = org.apache.commons.lang3.StringUtils.defaultIfBlank(getCellValue(cell), Strings.EMPTY);
                    if (cell.getColumnIndex() == WFHConstants.ID) {
                        flashCard.flashCardId(Long.parseLong(cellValue));
                    }
                    if (cell.getColumnIndex() == WFHConstants.FRONT_VALUE) {
                        List<CharacterDTO> frontValues = this.renderFrontValue(cellValue);
                        frontValue.content(frontValues);
                    }
                    if (cell.getColumnIndex() == WFHConstants.BACK_VALUE) {
                        String value = this.renderBackValue(cellValue);
                        backValue.content(value);
                    }
                    if (cell.getColumnIndex() == WFHConstants.TARGET_SKILL_NAME) {
                        flashCard.targetSkillName(cellValue);
                    }
                }
                String frontValuesJson = mapper.writeValueAsString(frontValue.build());
                String backValuesJson = mapper.writeValueAsString(backValue.build());
                flashCard.frontValue(frontValuesJson);
                flashCard.backValue(backValuesJson);
                flashCards.add(flashCard.build());
            }
//                    flashCardEntityRepository.saveAll(flashCards);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flashCards;
    }

    private List<CharacterDTO> renderFrontValue(String cellValue) {
        List<CharacterDTO> characterDTOs = new ArrayList<>();
        if (StringUtils.hasText(cellValue)) {
            // Split only for last ","
            String[] values = cellValue.split(",(?=[^,]*$)");
            String[] chars = values[0].split(Strings.EMPTY);
            Map<Integer, CharacterDTO> charsMap = IntStream.range(0, chars.length)
                    .boxed()
                    .collect(Collectors.toMap(
                            idx -> idx,
                            idx -> CharacterDTO.builder()
                                    .id(idx + 1)
                                    .character(chars[idx])
                                    .isHeart(false).build()
                    ));
            if (values.length > 1) {
                String[] heartedPositions = values[1].split(WFHConstants.VERTICAL_BAR);
                List<Integer> heartedIndexes = Arrays.stream(heartedPositions)
                        .map(Integer::parseInt).collect(Collectors.toList());
                heartedIndexes.forEach(idx -> charsMap.get(idx).setIsHeart(true));
                characterDTOs.addAll(charsMap.values());
            } else {
                characterDTOs = new ArrayList<>(charsMap.values());
            }
        }
        return characterDTOs;
    }

    private String renderBackValue(String cellValue) {
        if (StringUtils.hasText(cellValue)) {
            // Pattern split for last comma
            String[] values = cellValue.split(",(?=[^,]*$)");
            String[] words = values[0].split(org.apache.commons.lang3.StringUtils.SPACE);
            if (values.length > 1) {
                String[] boldPositions = values[1].split(WFHConstants.VERTICAL_BAR);
                List<Integer> boldIndexes = Arrays.stream(boldPositions)
                        .map(Integer::parseInt).collect(Collectors.toList());
                Map<Integer, String> wordsMap = IntStream.range(0, words.length)
                        .boxed()
                        .collect(Collectors.toMap(
                                idx -> idx,
                                idx -> words[idx]
                        ));
                boldIndexes.forEach(idx -> {
                    if (idx == -1) {
                        String lastWord = wordsMap.get(words.length - 1);
                        String oldWord = lastWord.substring(0, lastWord.length() - 1);
                        String newWord = WFHConstants.TAG_BOLD_OPEN + oldWord + WFHConstants.TAG_BOLD_CLOSE;
                        wordsMap.put(words.length - 1, lastWord.replace(oldWord, newWord));
                    } else {
                        wordsMap.put(idx, WFHConstants.TAG_BOLD_OPEN + wordsMap.get(idx) + WFHConstants.TAG_BOLD_CLOSE);
                    }
                });
                return String.join(org.apache.commons.lang3.StringUtils.SPACE, wordsMap.values());
            } else {
                return values[0];
            }
        }
        return Strings.EMPTY;
    }

    // Read file BRS_HFW_Manip_Kit_Interactive_Flash_Cards_v_0
    // Read file cardflip_v2.xml
    public String getAllFlashCardsAsHTML(HttpServletResponse response, GetAllFlashCardsRequest request) throws ParserConfigurationException, IOException, SAXException {
        List<FlashCardXMLEntity> flashCardXMLs = xmlService.readXMLFile(request.getFilePathXml());
        try (FileInputStream fis = new FileInputStream(request.getFilePathExcel()); XSSFWorkbook workbook = new XSSFWorkbook(fis)) {
            XSSFSheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == WFHConstants.NO) {
                    continue;
                }
                Map<Long, String> idFrontValueOriginMap = new HashMap<>();
                long id = 0L;
                for (Cell cell : row) {
                    String cellValue =
                            org.apache.commons.lang3.StringUtils.defaultIfBlank(getCellValue(cell), Strings.EMPTY);
                    if (cell.getColumnIndex() == WFHConstants.ID) {
                        id = Long.parseLong(cellValue);
                        idFrontValueOriginMap.put(id, "");
                    }
                    if (cell.getColumnIndex() == WFHConstants.FRONT_VALUE) {
                        idFrontValueOriginMap.put(id, cellValue);
                    }
                }
                flashCardXMLs = flashCardXMLs
                        .stream()
                        .peek(f -> f.setFrontValueOrigin(idFrontValueOriginMap.get(f.getFlashCardIdXml())))
                        .collect(Collectors.toList());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.renderAllFlashCardHTML(flashCardXMLs);
    }

    private String renderAllFlashCardHTML(List<FlashCardXMLEntity> flashCardXMLs) {
        StringBuilder allFlashCardHTML = new StringBuilder("");
        String header = "<div class=\"question-questionStem question-questionStem-1-column\">\n" +
                "<div class=\"question-stem-content\">\n" +
                "<div begin=\"1\" cid=\"build_flash_card_q01_ans01\" ctype=\"card\">\n" +
                "<div class=\"cardContents\">\n";
        String footer = "</div>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div>";
        allFlashCardHTML.append(header);
        flashCardXMLs.forEach(f -> {
            String oneFlashCardHTML = String.format("<div class=\"id\"><data>%d</data></div>\n" +
                            "\n" +
                            "<div class=\"category\"><data>%s</data></div>\n" +
                            "\n" +
                            "<div class=\"audio_first\" ctype=\"audio\"><data>/content/803090/BRST2_381/audio/%s</data></div>\n" +
                            "\n" +
                            "<div class=\"word\"><data>%s</data></div>\n" +
                            "\n" +
                            "<div class=\"terms\"><data>%s</data></div>\n" +
                            "\n" +
                            "<div class=\"audio_last\" ctype=\"audio\"><data>/content/803090/BRST2_381/audio/%s</data></div>\n" +
                            "\n" +
                            "<div class=\"def\"><data>%s</data></div>\n" +
                            "\n" +
                            "<div class=\"set\"><data>%s</data></div>\n",
                    f.getFlashCardIdXml(), f.getCategoryXml(), f.getFrontAudioXml(), f.getFrontValueOrigin(),
                    f.getFrontValueXml(), f.getBackAudioXml(), f.getBackValueXml(), f.getModuleXml());
            allFlashCardHTML.append(oneFlashCardHTML);
        });
        return allFlashCardHTML.append(footer).toString();
    }
}
