package br.com.softctrl.resthift.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
@RestController
@RequestMapping("/db")
public class TableController {

	private TableRepository tableRepository;

	/**
	 * 
	 * @param tableRepository
	 */
	@Autowired
	public TableController(TableRepository tableRepository) {
		this.tableRepository = tableRepository;
	}

	/**
	 * 
	 * @param table
	 * @param limit
	 * @return
	 */
	@RequestMapping(value = "/{table}", produces = { "application/json" }, method = { RequestMethod.GET })
	public List<Map<String, Object>> select(@PathVariable String table,
			@RequestParam(value = "limit", required = false, defaultValue = "100") int limit) {

		return tableRepository.select("SELECT * FROM " + table + " LIMIT " + limit);

	}

	/**
	 * 
	 * @param query
	 * @return
	 */
	@RequestMapping(value = "/_select", produces = { "application/json" }, method = { RequestMethod.POST })
	public List<Map<String, Object>> executeQuery(@RequestBody String query) {

		return tableRepository.select(query);

	}

}
