/**
 * Author:  eugenio
 * Created: 18/11/2015
 */

SELECT DISTINCT 
    dp.`CPF_PFisica`, 
    pf.`Nome_PFisica`, 
    pf.`Codigo_PFisica`, 
    epf.`Email_Site`,
    tu.`Nome_Turma`, 
    m.`Codigo_Situacao_Aluno`, 
    mens.`Data_Vencimento`, 
    mens.`Valor_Mensalidade`, 
    mens.`Nosso_Numero`, 
    mens.`Codigo_Situacao_Mensalidade`,
    mens.`Numero_Mensalidade`,
    mens.`Percentual_Multa`,
    mens.`Percentual_Juros`,
    tabmens.`Qtde_Mensalidades`,
    ep.`Endereco`,
    ep.`Complemento_Endereco`,
    bai.`Nome_Bairro`,
    cid.`Nome_Cidade`,
    est.`Sigla_Estado`,
    ep.`CEP_Endereco`
FROM ((((((((((((((`PFisicas` pf
INNER JOIN `Matriculas` m ON m.`Codigo_PFisica` = pf.`Codigo_PFisica`)
INNER JOIN `PeriodosLetivos` pl ON pl.`Codigo_Periodo_Letivo` = m.`Codigo_Periodo_Letivo`)
INNER JOIN `Cursos` c ON c.`Codigo_Curso` = m.`Codigo_Curso`)
INNER JOIN `Series` s ON s.`Codigo_Serie` = m.`Codigo_Serie`)
INNER JOIN `Turnos` t ON t.`Codigo_Turno` = m.`Codigo_Turno`)
LEFT JOIN `EmailSitePFisicas` epf ON epf.`Codigo_PFisica` = pf.`Codigo_PFisica` )
INNER JOIN `TurmasEscola` tu ON tu.`Codigo_Curso` = c.`Codigo_Curso` AND tu.`Codigo_Serie`= s.`Codigo_Serie` AND tu.`Codigo_Turno` = t.`Codigo_Turno`)
INNER JOIN `Mensalidades` mens ON mens.`Codigo_Periodo_Letivo` = pl.`Codigo_Periodo_Letivo`  
AND mens.`Codigo_Curso` = c.`Codigo_Curso` 
AND mens.`Codigo_Serie` = s.`Codigo_Serie` 
AND mens.`Codigo_PFisica` = pf.`Codigo_PFisica` )
INNER JOIN `TabelasMensalidades` tabmens ON tabmens.`Codigo_Periodo_Letivo` = pl.`Codigo_Periodo_Letivo` 
AND tabmens.`Codigo_Curso` = c.`Codigo_Curso` 
AND tabmens.`Codigo_Serie` = s.`Codigo_Serie`  )
INNER JOIN `DocumentosPFisicas` dp ON dp.`Codigo_PFisica` = pf.`Codigo_PFisica`)
LEFT JOIN `EnderecosPFisicas` ep ON ep.`Codigo_PFisica` = pf.`Codigo_PFisica` ) 
LEFT JOIN `Estados` est ON est.`Codigo_Estado` = ep.`Codigo_Estado`  )
LEFT JOIN `Cidades` cid ON cid.`Codigo_Cidade` = ep.`Codigo_Cidade` AND cid.`Codigo_Estado` = ep.`Codigo_Estado` )
LEFT JOIN `Bairros` bai ON bai.`Codigo_Bairro` = ep.`Codigo_Bairro` AND bai.`Codigo_Cidade` = ep.`Codigo_Cidade` AND bai.`Codigo_Estado` = ep.`Codigo_Estado` )
WHERE 1 = 1 
AND YEAR(mens.`Data_Vencimento`) = 2016 
AND tu.`Codigo_Turma` = '020'
AND m.`Codigo_Situacao_Aluno` = '01' 
AND mens.`Codigo_Situacao_Mensalidade` = '1' 
AND MONTH(mens.`Data_Vencimento`) = 3
ORDER BY pf.`Nome_PFisica`;

-- UPDATE `Mensalidades` SET `Codigo_Situacao_Mensalidade` = '2', `Data_Pagamento` = ?, `Valor_Baixa` = ?
-- WHERE `Codigo_PFisica` = ? AND `Nosso_Numero` = ?;