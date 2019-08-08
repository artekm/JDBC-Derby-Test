import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResultSetPrinter {
	public void printAll(ResultSet rs) throws SQLException {
		int numColumns = rs.getMetaData().getColumnCount();

		List<String> dataFields = new ArrayList<>();

		for (int col = 1; col <= numColumns; col++)
			dataFields.add(rs.getMetaData().getColumnName(col));

		while (rs.next())
			for (int col = 1; col <= numColumns; col++)
				dataFields.add(rs.getString(col));

		int[] columnsWidth = calculateColumnsWidth(dataFields, numColumns);
		String underline = prepareUnderline(columnsWidth);

		int pos = 0;
		for (String name : dataFields) {
			System.out.printf(" %" + columnsWidth[pos % numColumns] + "s |", name);
			pos++;
			if (pos % numColumns == 0) {
				System.out.println();
				if (pos / numColumns == 1)
					System.out.println(underline);
			}
		}
		System.out.println(underline);
	}

	public String formatAll(ResultSet rs) throws SQLException {
		StringBuilder result = new StringBuilder("");

		int numColumns = rs.getMetaData().getColumnCount();

		List<String> dataFields = new ArrayList<>();

		for (int col = 1; col <= numColumns; col++)
			dataFields.add(rs.getMetaData().getColumnName(col));

		while (rs.next())
			for (int col = 1; col <= numColumns; col++)
				dataFields.add(rs.getString(col));

		int[] columnsWidth = calculateColumnsWidth(dataFields, numColumns);
		String underline = prepareUnderline(columnsWidth);

		int pos = 0;
		for (String name : dataFields) {
			result=result.append(String.format(" %" + columnsWidth[pos % numColumns] + "s |", name));
			pos++;
			if (pos % numColumns == 0) {
				result.append("\n");
				if (pos / numColumns == 1)
					result = result.append(underline).append("\n");
			}
		}
		result = result.append(underline);
		return result.toString();
	}

	private int[] calculateColumnsWidth(List<String> dataFields, int numColumns) {
		int[] columnsWidth = new int[numColumns];
		int pos = 0;
		for (String name : dataFields)
			columnsWidth[pos % numColumns] = Math.max(columnsWidth[pos++ % numColumns],
					name == null ? 4 : name.length());
		return columnsWidth;
	}

	private String prepareUnderline(int[] columnsWidth) {
		int totalWidth = 0;
		for (int width : columnsWidth)
			totalWidth = totalWidth + width + 3;
		char[] underline = new char[totalWidth];
		Arrays.fill(underline, '-');
		return new String(underline);
	}
}
