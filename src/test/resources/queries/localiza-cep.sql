/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  eugenio
 * Created: 28/01/2016
 */
SELECT DISTINCT 
    dp.`CPF_PFisica`, 
    pf.`Nome_PFisica`, 
    pf.`Codigo_PFisica`, 
    epf.`Email_Site`,
    ep.`Endereco`,
    ep.`Complemento_Endereco`,
    bai.`Nome_Bairro`,
    cid.`Nome_Cidade`,
    est.`Sigla_Estado`,
    ep.`CEP_Endereco`
FROM ((((((`PFisicas` pf
INNER JOIN `EmailSitePFisicas` epf ON epf.`Codigo_PFisica` = pf.`Codigo_PFisica` )
INNER JOIN `DocumentosPFisicas` dp ON dp.`Codigo_PFisica` = pf.`Codigo_PFisica`)
INNER JOIN `EnderecosPFisicas` ep ON ep.`Codigo_PFisica` = pf.`Codigo_PFisica` ) 
INNER JOIN `Estados` est ON est.`Codigo_Estado` = ep.`Codigo_Estado`  )
INNER JOIN `Cidades` cid ON cid.`Codigo_Cidade` = ep.`Codigo_Cidade` 
AND cid.`Codigo_Estado` = ep.`Codigo_Estado` )
LEFT  JOIN `Bairros` bai ON bai.`Codigo_Bairro` = ep.`Codigo_Bairro` 
AND bai.`Codigo_Cidade` = ep.`Codigo_Cidade` AND bai.`Codigo_Estado` = ep.`Codigo_Estado` )
WHERE 1 = 1 
AND pf.`Codigo_PFisica` = '004073'
ORDER BY pf.`Codigo_PFisica`;

SELECT DISTINCT 
    pf.`Codigo_PFisica`, 
    COUNT(pf.`Codigo_PFisica`)
FROM ((((((`PFisicas` pf
INNER JOIN `EmailSitePFisicas` epf ON epf.`Codigo_PFisica` = pf.`Codigo_PFisica` )
INNER JOIN `DocumentosPFisicas` dp ON dp.`Codigo_PFisica` = pf.`Codigo_PFisica`)
INNER JOIN `EnderecosPFisicas` ep ON ep.`Codigo_PFisica` = pf.`Codigo_PFisica` ) 
INNER JOIN `Estados` est ON est.`Codigo_Estado` = ep.`Codigo_Estado`  )
INNER JOIN `Cidades` cid ON cid.`Codigo_Cidade` = ep.`Codigo_Cidade` 
AND cid.`Codigo_Estado` = ep.`Codigo_Estado` )
LEFT  JOIN `Bairros` bai ON bai.`Codigo_Bairro` = ep.`Codigo_Bairro` 
AND bai.`Codigo_Cidade` = ep.`Codigo_Cidade` AND bai.`Codigo_Estado` = ep.`Codigo_Estado` )
WHERE 1 = 1 
--AND pf.`Codigo_PFisica` = '004073'
GROUP BY pf.`Codigo_PFisica`
ORDER BY 2 DESC;


SELECT DISTINCT 
    ep.`CEP_Endereco`
FROM ((`PFisicas` pf
INNER JOIN `DocumentosPFisicas` dp ON dp.`Codigo_PFisica` = pf.`Codigo_PFisica` )
INNER JOIN `EnderecosPFisicas` ep ON ep.`Codigo_PFisica` = pf.`Codigo_PFisica` ) 
WHERE 1 = 1 
AND dp.`CPF_PFisica` = '00846614375'

