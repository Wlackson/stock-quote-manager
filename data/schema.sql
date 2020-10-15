CREATE TABLE IF NOT EXISTS `quotes` (
                          `quote_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
                          `quote_stock_id` char(10) NOT NULL,
                          `quote_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          `quote_value` decimal(6,2) NOT NULL,
                          PRIMARY KEY (`quote_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;