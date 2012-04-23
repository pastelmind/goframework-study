/*******************************************************************************
 * 
 * Copyright 2007 Randall
 * 
 * This file is part of gomule.
 * 
 * gomule is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * gomule is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * gomlue; if not, write to the Free Software Foundation, Inc., 51 Franklin St,
 * Fifth Floor, Boston, MA 02110-1301 USA
 *  
 ******************************************************************************/
package gomule.d2Files;

import java.io.File;
import java.util.HashMap;

import collections.CollectionHelpers;

public class D2TblFile {
    private static String sMod;

    private static D2TblFile ENG_PATCH;
    private static D2TblFile ENG_EXP;
    private static D2TblFile ENG_STRING;

    private HashMap<String, String> iHashMap = CollectionHelpers.newHashMap();

    public static void readAllFiles( String pMod ) {
        sMod = pMod;
        ENG_PATCH = new D2TblFile( "patchstring" );
        ENG_EXP = new D2TblFile( "expansionstring" );
        ENG_STRING = new D2TblFile( "string" );
    }

    public static String getString( String pKey ) {
        String lValue = ENG_PATCH.getValue( pKey );

        if( lValue == null ) {
            lValue = ENG_EXP.getValue( pKey );
            if( lValue == null ) {
                lValue = ENG_STRING.getValue( pKey );
            }
        }

        return lValue;
    }

    private D2TblFile( String pFileName ) {
        try {
            String lFileName = sMod + File.separator + pFileName + ".tbl";
            D2FileReader lFileReader = new D2FileReader( lFileName );
            lFileReader.getCounterInt( 8 );

            lFileReader.increaseCounter( 8 );

            int lNumElementsOffset = lFileReader.getCounterInt( 16 );
            int lHashTableSizeOffset = lFileReader.getCounterInt( 16 );

            lFileReader.increaseCounter( 16 );

            lFileReader.getCounterInt( 8 );
            lFileReader.getCounterInt( 16 );

            lFileReader.increaseCounter( 16 );

            lFileReader.getCounterInt( 16 );

            lFileReader.increaseCounter( 16 );

            lFileReader.getCounterInt( 16 );

            lFileReader.increaseCounter( 16 );

            for( int i = 0; i < lNumElementsOffset; i++ ) {
                lFileReader.increaseCounter( 2 * 8 );
            }

            for( int i = 0; i < lHashTableSizeOffset; i++ ) {
                lFileReader.increaseCounter( 17 * 8 );
            }

            String lKey = lFileReader.getCounterString();
            String lValue = lFileReader.getCounterString();
            while( lKey != null ) {

                if( lKey.startsWith( "Skill" ) && lKey.indexOf( "223" ) > 0 && lValue.indexOf( "wolf" ) > 0 ) {
                    //PROPLEM WITH PLAGUE POPPY (SKILL 223) BEING SET AND THEN WEREWOLF OVERWRITING IT FOR SOME REASON.
                    //THIS IGNORES THE SECOND WRITE OF WEREWOLF.
                } else {
                    iHashMap.put( lKey, lValue );
                }
                lKey = lFileReader.getCounterString();
                lValue = lFileReader.getCounterString();

            }
        } catch( Exception pEx ) {
            //TODO fix exception handling...
            //            D2FileManager.displayErrorDialog( pEx );
        }
    }

    public String getValue( String pKey ) {
        return iHashMap.get( pKey );
    }
}
