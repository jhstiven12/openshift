package com.test.processor;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import com.test.dto.Request;

@Component
public class UserProcessor implements Processor {
	private List<Request> listUser = new ArrayList<Request>();

	@Override
	public void process(Exchange exchange) throws Exception {

		List<Request> list = new ArrayList<Request>();

		switch (String.valueOf(exchange.getProperty("serviceRest"))) {
		case "create":
			list = listUser;
			list.add((Request) exchange.getIn().getBody());
			listUser = list;
			list = new ArrayList<Request>();
			break;
		case "update":
			String documentUP = String.valueOf(exchange.getIn().getHeader("document"));

			List<Request> listUpdate = new ArrayList<>(listUser);
			int iu = 0;
			for (Request request : listUser) {
				if (request.getId().equals(documentUP)) {
					request = (Request) exchange.getIn().getBody();
					listUpdate.set(iu, request);
				}
				iu++;
			}
			list = listUpdate;
			listUser = list;
			list = new ArrayList<Request>();
			break;
		case "delete":
			String documentDE = String.valueOf(exchange.getIn().getHeader("document"));
			List<Request> listDelete = new ArrayList<>(listUser);
			int i = 0;
			for (Request request : listUser) {
				if (request.getId().equals(documentDE)) {
					listDelete.remove(i);
				}
				i++;
			}
			list = listDelete;
			listUser = list;
			list = new ArrayList<Request>();
			break;
		case "list":
			String documentLI = String.valueOf(exchange.getIn().getHeader("document"));
			for (Request request : listUser) {
				if (request.getId().equals(documentLI)) {
					list.add(request);
					break;
				}
			}
			break;
		case "listall":
			list = listUser;
			break;
		default:
			list = listUser;
			break;
		}
		exchange.getIn().setBody(list);
		exchange.getOut().setBody(list);
	}

}
