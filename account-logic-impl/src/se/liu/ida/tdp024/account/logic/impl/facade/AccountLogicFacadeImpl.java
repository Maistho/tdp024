package se.liu.ida.tdp024.account.logic.impl.facade;

import java.util.List;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.data.api.exception.AccountInputParameterException;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializer;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializerImpl;
import se.liu.ida.tdp024.account.util.http.HTTPHelper;
import se.liu.ida.tdp024.account.util.http.HTTPHelperImpl;
import se.liu.ida.tdp024.account.logic.api.util.FinalConstants;
import se.liu.ida.tdp024.account.logic.api.util.PersonDTO;
import se.liu.ida.tdp024.account.util.logger.AccountLogger;
import se.liu.ida.tdp024.account.util.logger.AccountLoggerMonlog;

public class AccountLogicFacadeImpl implements AccountLogicFacade {
    
    private AccountEntityFacade accountEntityFacade;
    private AccountJsonSerializer jsonSerializer = new AccountJsonSerializerImpl();
    private HTTPHelper http = new HTTPHelperImpl();
    
    public AccountLogicFacadeImpl(AccountEntityFacade accountEntityFacade) {
        this.accountEntityFacade = accountEntityFacade;
    }
    
    
    @Override
    public void create(
            String accounttype,
            String name,
            String bank)
            throws
            AccountInputParameterException
    {
        String personKey = jsonSerializer.fromJson(
                http.get(FinalConstants.PERSON_ENDPOINT+"find.name", "name", name),
                PersonDTO.class).getKey();
        String bankKey = jsonSerializer.fromJson(
                http.get(FinalConstants.BANK_ENDPOINT+"find.name", "name", bank),
                PersonDTO.class).getKey();
        
        
        
        try {            
            accountEntityFacade.create(accounttype, personKey, bankKey);
        } catch (AccountInputParameterException e) {
            throw e;
        }

    }

    @Override
    public String find(String name) {
        String personKey = jsonSerializer.fromJson(
                http.get(FinalConstants.PERSON_ENDPOINT+"find.name", "name", name),
                PersonDTO.class).getKey();
        List<Account> results = accountEntityFacade.findAllByName(personKey);
        return jsonSerializer.toJson(results);
    }
    
}
