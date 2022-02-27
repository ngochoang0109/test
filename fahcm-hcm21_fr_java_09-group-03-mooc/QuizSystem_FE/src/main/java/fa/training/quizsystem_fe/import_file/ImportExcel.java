package fa.training.quizsystem_fe.import_file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.SystemOutLogger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import fa.training.quizsystem_fe.dtos.Answer;
import fa.training.quizsystem_fe.dtos.Question;
import fa.training.quizsystem_fe.dtos.Quiz;

public class ImportExcel {

	@SuppressWarnings({ "unchecked" })
	public static Quiz parseExcelFile(InputStream is) {
		try {
			Workbook workbook = new XSSFWorkbook(is);

			Sheet sheetQuestios = workbook.getSheet("Questions");
			Sheet sheetQuiz = workbook.getSheet("Quiz");

			Quiz quiz = getInfoQuiz(sheetQuiz);
			quiz.setQuestions(getInforQuestion(sheetQuestios));

			quiz.setPlays(0);
			// Close WorkBook
			workbook.close();
			return quiz;
		} catch (IOException e) {
			throw new RuntimeException("FAIL! -> message = " + e.getMessage());
		}
	}

	public static List getInforQuestion(Sheet sheetQuestios) throws IOException {
		// get iterator of sheet
		Iterator rows = sheetQuestios.iterator();

		List listQuestion = new ArrayList();

		// number row of sheet
		int rowNumber = 0;

		while (rows.hasNext()) {
			Row currentRow = (Row) rows.next();

			// skip header
			if (rowNumber == 0) {
				rowNumber++;
				continue;
			}

			Iterator cellsInRow = currentRow.iterator();

			Question question = new Question();

			int cellIndex = 0;
			List listAnswer = new ArrayList();
			while (cellsInRow.hasNext()) {
				Cell currentCell = (Cell) cellsInRow.next();
				switch (cellIndex) {
				case 0:// get name question
					question.setText(currentCell.getStringCellValue());
					break;
				case 1:// get multiple
					question.setMultiple(currentCell.getStringCellValue().equalsIgnoreCase("multiple"));
					break;
				default:// get answers
					if(!currentCell.getStringCellValue().isBlank()) {
						Answer answer = new Answer();
						answer.setText(currentCell.getStringCellValue());// get name answer
						currentCell = (Cell) cellsInRow.next();// get next column
						answer.setCorrect(currentCell.getBooleanCellValue());// get correct of answer
						listAnswer.add(answer);
					}
					break;
				}

				question.setAnswers(listAnswer);
				cellIndex++;
			}
			listQuestion.add(question);
		}
		System.out.println(listQuestion.toString());
		return listQuestion;
	}

	public static MultipartFile convertFiletoMutlpart(String link) throws IOException {
		File file = new File(link);
		DiskFileItem fileItem = new DiskFileItem("file", "text/plain", false, file.getName(), (int) file.length(),
				file.getParentFile());
		fileItem.getOutputStream();
		MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
		return multipartFile;
	}

	public static Quiz getInfoQuiz(Sheet sheetQuiz) {
		// get iterator of sheet
		Iterator rows = sheetQuiz.iterator();

		Quiz quiz = new Quiz();
		rows.next();// skip header
		Row currentRow = (Row) rows.next();

		Iterator cellsInRow = currentRow.iterator();

		int cellIndex = 0;
		while (cellsInRow.hasNext()) {
			Cell currentCell = (Cell) cellsInRow.next();
			switch (cellIndex) {
			case 0:// get title of qiz
				quiz.setTitle(currentCell.getStringCellValue());
				break;
			case 1:// get description
				quiz.setDescription(currentCell.getStringCellValue());
				break;
			case 2:// get education level
				quiz.setEducationLevel(currentCell.getStringCellValue());
				break;
			case 3:// get max attempts
				quiz.setMaxAttempts((int) currentCell.getNumericCellValue());
				;
				break;
			case 4:// get education level
				quiz.setTimeLimit((int) currentCell.getNumericCellValue());
				;
				break;

			default:// get answers
				break;
			}

			cellIndex++;
		}
		return quiz;

	}

	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("E:\\Book1.xlsx");
		InputStream inputStream = new FileInputStream(file);

		parseExcelFile(inputStream);
	}
}
