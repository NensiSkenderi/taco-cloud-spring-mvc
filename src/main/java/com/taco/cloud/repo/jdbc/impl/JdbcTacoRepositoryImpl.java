package com.taco.cloud.repo.jdbc.impl;

import com.taco.cloud.model.Taco;
import com.taco.cloud.model.TacoIngredient;
import com.taco.cloud.repo.jdbc.JdbcTacoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;

@Repository
public class JdbcTacoRepositoryImpl implements JdbcTacoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    /*
     Saving a taco design requires that you
     also save the ingredients associated with that taco

     we need a save() method that starts by saving the essential
     taco info (for example, the name and time of creation),
     and then inserts one row into Taco_Taco_Ingredients for each ingredient
     in the Taco object.
     */
    @Override
    public Taco save(Taco taco) {
        long tacoId = saveTacoInfo(taco);
        taco.setId(tacoId);
        for (TacoIngredient ingredient : taco.getIngredients()) {
            saveTacoIngredientForEachIngredient(ingredient.getName(), tacoId);
        }
        return taco;
    }

    private long saveTacoInfo(Taco taco) {
        taco.setCreatedAt(new Date());
        PreparedStatementCreatorFactory preparedStatementCreatorFactory = new PreparedStatementCreatorFactory(
                "insert into Taco (name, createdAt) values (?, ?)",
                Types.VARCHAR, Types.TIMESTAMP);

        PreparedStatementCreator psc = preparedStatementCreatorFactory.newPreparedStatementCreator(
                Arrays.asList(
                        taco.getName(),
                        new Timestamp(taco.getCreatedAt().getTime())));
        preparedStatementCreatorFactory.setReturnGeneratedKeys(Boolean.TRUE);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(psc, keyHolder);

        /*
        in order to use keyholder we must also create a PreparedStatementCreator
         */
        return keyHolder.getKey().longValue(); //to get the id that we just inserted
    }

    //save taco id and taco ingredient id
    private void saveTacoIngredientForEachIngredient(String tacoIngredient, long tacoId) {
        jdbcTemplate.update("insert into Taco_Taco_Ingredients (taco, ingredient) " +
                        "values (?, ?)",
                tacoId, tacoIngredient);
    }


}
