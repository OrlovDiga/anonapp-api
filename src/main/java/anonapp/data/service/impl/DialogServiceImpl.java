package anonapp.data.service.impl;

import anonapp.data.repo.DialogRepo;
import anonapp.data.service.DialogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Orlov Diga
 */
/*@Service*/
public class DialogServiceImpl implements DialogService {

    private final DialogRepo dialogRepo;

/*
    @Autowired
*/
    public DialogServiceImpl(DialogRepo dialogRepo) {
        this.dialogRepo = dialogRepo;
    }




}
