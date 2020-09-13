package com.johnbryce.app.security;

import com.johnbryce.app.service.ClientService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomSession {

	private ClientService clientService;
	private long date;
}
