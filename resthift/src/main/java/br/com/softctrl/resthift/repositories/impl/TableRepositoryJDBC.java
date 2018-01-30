package br.com.softctrl.resthift.repositories.impl;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.softctrl.resthift.repositories.TableRepository;

/*
GNU LESSER GENERAL PUBLIC LICENSE (LGPL v3)

Copyright (c) 2018 Carlos Timoshenko Rodrigues Lopes
http://www.0x09.com.br

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

/**
 * 
 * @author carlostimoshenkorodrigueslopes@gmail.com
 *
 */
@Repository
public class TableRepositoryJDBC implements TableRepository {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1468829888742090015L;

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public TableRepositoryJDBC(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.softctrl.resthift.repositories.TableRepository#select(java.lang.String)
	 */
	@Override
	public List<Map<String, Object>> select(String sql) {

		return jdbcTemplate.query(sql, (resultSet, rowNum) -> {

			final int count = resultSet.getMetaData().getColumnCount() + 1;
			final ResultSetMetaData rsm = resultSet.getMetaData();
			Map<String, Object> row = new HashMap<>();
			try {
				for (int idx = 1; idx < count; idx++) {
					row.put(rsm.getColumnName(idx), resultSet.getObject(idx, getTypeClass(rsm, idx)));
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return row;

		});

	}

	/**
	 * 
	 * @param metaData
	 * @param columnIndex
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static final Class<?> getTypeClass(final ResultSetMetaData metaData, final int columnIndex)
			throws ClassNotFoundException, SQLException {
		return Class.forName(metaData.getColumnClassName(columnIndex));
	}

}
