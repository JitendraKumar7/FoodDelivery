package com.rch.etawah.InterfaceUtil;

/**
 * Created by hp on 2/26/2018.
 */

public interface SqlQueries {

    /**
     * <p>Give query of SQL which can retrieve all data from Database</p>
     *
     * @return give SQl Query for retreiving all data
     */
    String retrieving();

    /**
     * <p>Give query of SQL which can update data in Database</p>
     *
     * @return give SQl Query for Updating data
     */
    String update();

    /**
     * <p>Give query of SQL which can insert data in Database</p>
     *
     * @return give SQl Query for inserting  data
     */
    String insert();

    /**
     * <p>Give query of SQL which can delete  data from Database</p>
     *
     * @return give SQl Query for delete data
     */
    String delete();


    String retrieveSpecificType();

    String retrieveSpecificTags();

    String deleteSpecificType();

    String findLastInsertId();

}
