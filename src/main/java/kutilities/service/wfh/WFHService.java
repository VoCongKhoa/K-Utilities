package kutilities.service.wfh;

import com.fasterxml.jackson.databind.ObjectMapper;
import kutilities.domain.constants.wfh.WFHConstants;
import kutilities.domain.dto.request.wfh.ReadFlashCashRequest;
import kutilities.domain.dto.wfh.BackValue;
import kutilities.domain.dto.wfh.CharacterDto;
import kutilities.domain.dto.wfh.FrontValue;
import kutilities.domain.entity.wfh.FlashCardEntity;
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

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static kutilities.utils.ExcelUtils.getCellValue;

@Service
@RequiredArgsConstructor
@Slf4j
public class WFHService {
    public final ExcelService excelService;
    public final FlashCardEntityRepository flashCardEntityRepository;
    private final int NO = 0;
    private final int ID = 0;
    private final int FRONT_VALUE = 1;
    private final int FRONT_AUDIO = 2;
    private final int BACK_VALUE = 3;
    private final int BACK_AUDIO = 4;
    private final int TARGET_SKILL_NAME = 6;

    //    @Transactional
    public List<FlashCardEntity> readFlashCards(HttpServletResponse response, ReadFlashCashRequest request) {
        List<FlashCardEntity> flashCards = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try (FileInputStream fis = new FileInputStream(request.getFilePath());
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {
            XSSFSheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == NO) {
                    continue;
                }

                FlashCardEntity.FlashCardEntityBuilder flashCard = FlashCardEntity.builder();
                FrontValue.FrontValueBuilder frontValue = FrontValue.builder();
                BackValue.BackValueBuilder backValue = BackValue.builder();
                for (Cell cell : row) {
                    String cellValue = org.apache.commons.lang3.StringUtils.defaultIfBlank(getCellValue(cell), Strings.EMPTY);
                    if (cell.getColumnIndex() == ID) {
                        flashCard.flashCardId(Long.parseLong(cellValue));
                    }
                    if (cell.getColumnIndex() == FRONT_VALUE) {
                        List<CharacterDto> frontValues = this.renderFrontValue(cellValue);
                        frontValue.value(frontValues);
                    }
                    if (cell.getColumnIndex() == FRONT_AUDIO) {
                        frontValue.audio(cellValue);
                    }
                    if (cell.getColumnIndex() == BACK_VALUE) {
                        String value = this.renderBackValue(cellValue);
                        backValue.value(value);
                    }
                    if (cell.getColumnIndex() == BACK_AUDIO) {
                        backValue.audio(cellValue);
                    }
                    if (cell.getColumnIndex() == TARGET_SKILL_NAME) {
                        flashCard.targetSkillName(cellValue);
                    }
                }
                String frontValuesJson = mapper.writeValueAsString(frontValue.build());
                String backValuesJson = mapper.writeValueAsString(backValue.build());
                flashCard.frontValue(frontValuesJson);
                flashCard.backValue(backValuesJson);
                flashCard.programId(ThreadLocalRandom.current().nextLong(10000000, 100000000));
                flashCards.add(flashCard.build());
            }
            //        flashCardEntityRepository.saveAll(flashCards);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flashCards;
    }

    private List<CharacterDto> renderFrontValue(String cellValue) {
        List<CharacterDto> characterDtos = new ArrayList<>();
        if (StringUtils.hasText(cellValue)) {
            String[] values = cellValue.split(",(?=[^,]*$)");
            String[] chars = values[0].split(Strings.EMPTY);
            Map<Integer, CharacterDto> charsMap = IntStream.range(0, chars.length)
                    .boxed()
                    .collect(Collectors.toMap(
                            idx -> idx,
                            idx -> CharacterDto.builder()
                                    .character(chars[idx])
                                    .isHeart(false).build()
                    ));
            if (values.length > 1) {
                List<Integer> idxs = Arrays.stream(values[1].split("\\|"))
                        .map(Integer::parseInt).collect(Collectors.toList());
                idxs.forEach(idx -> charsMap.get(idx).setIsHeart(true));
                characterDtos.addAll(charsMap.values());
            } else {
                characterDtos = new ArrayList<>(charsMap.values());
            }
        }
        return characterDtos;
    }

    private String renderBackValue(String cellValue) {
        if (StringUtils.hasText(cellValue)) {
            // Pattern split for last comma
            String[] values = cellValue.split(",(?=[^,]*$)");
            String[] words = values[0].split(org.apache.commons.lang3.StringUtils.SPACE);
            if (values.length > 1) {
                List<Integer> idxs = Arrays.stream(values[1].split("\\|"))
                        .map(Integer::parseInt).collect(Collectors.toList());
                Map<Integer, String> wordsMap = IntStream.range(0, words.length)
                        .boxed()
                        .collect(Collectors.toMap(
                                idx -> idx,
                                idx -> words[idx]
                        ));
                idxs.forEach(idx -> {
                    if (idx == -1) {
                        String lastWord = wordsMap.get(words.length - 1);
                        String oldWord = lastWord.substring(0, lastWord.length() - 1);
                        String newWord = WFHConstants.TAG_OPEN_BOLD + oldWord + WFHConstants.TAG_CLOSE_BOLD;
                        wordsMap.put(words.length - 1, lastWord.replace(oldWord, newWord));
                    } else {
                        wordsMap.put(idx, WFHConstants.TAG_OPEN_BOLD + wordsMap.get(idx) + WFHConstants.TAG_CLOSE_BOLD);
                    }
                });
                return String.join(org.apache.commons.lang3.StringUtils.SPACE, wordsMap.values());
            } else {
                return values[0];
            }
        }
        return Strings.EMPTY;
    }
}
